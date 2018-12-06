package com.example.android.bakingappproject.ui.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingappproject.R;
import com.example.android.bakingappproject.data.database.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetailsFragment extends Fragment implements ExoPlayer.EventListener {

    @BindView(R.id.textview_description)
    TextView descriptionTextView;

    @BindView(R.id.player_view)
    SimpleExoPlayerView playerView;

    public static final String KEY_CURRENT_STEP = RecipeStepFragment.KEY_CURRENT_STEP;
    public static final String KEY_VIDEO_CURRENT_POSITION = "videoCurrentPositionKey";
    public Steps currentStep;

    private static final String TAG = RecipeStepDetailsFragment.class.getSimpleName();
    private SimpleExoPlayer mExoPlayer;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private long mCurrentPosition = 0;

    public RecipeStepDetailsFragment() {
    }

    public static RecipeStepDetailsFragment newInstance(Steps currentStep) {
        RecipeStepDetailsFragment stepDetailsFragment = new RecipeStepDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_CURRENT_STEP, (Parcelable) currentStep);
        stepDetailsFragment.setArguments(args);
        return stepDetailsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details_step, container, false);
        ButterKnife.bind(this, rootView);


        if (savedInstanceState != null) {
            currentStep = savedInstanceState.getParcelable(KEY_CURRENT_STEP);
            mCurrentPosition = savedInstanceState.getLong(KEY_VIDEO_CURRENT_POSITION);
        } else {
            currentStep = getArguments().getParcelable(KEY_CURRENT_STEP);
        }
        descriptionTextView.setText(currentStep.getStepDescription());

        initializeMediaSession();

        String currentStepVideoUrl = currentStep.getStepVideoUrl();

        if (currentStepVideoUrl != null || !currentStepVideoUrl.isEmpty()) {
            Uri videoUri = Uri.parse(currentStepVideoUrl);
            initializePlayer(videoUri);
        }

        return rootView;
    }

    private void initializePlayer(Uri videoUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector,
                    loadControl);
            playerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(videoUri, new
                    DefaultDataSourceFactory(getContext(), userAgent), new
                    DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);

            if (mCurrentPosition != 0) mExoPlayer.seekTo(mCurrentPosition);
            mExoPlayer.setPlayWhenReady(true);
            mExoPlayer.addListener(this);
        }

    }


    private void initializeMediaSession() {
        mMediaSession = new MediaSessionCompat(getContext(), TAG);
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat
                .FLAG_HANDLES_TRANSPORT_CONTROLS);
        mMediaSession.setMediaButtonReceiver(null);
        mStateBuilder = new PlaybackStateCompat.Builder().setActions(PlaybackStateCompat
                .ACTION_PLAY | PlaybackStateCompat.ACTION_PAUSE | PlaybackStateCompat
                .ACTION_SKIP_TO_PREVIOUS | PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mMediaSession.setPlaybackState(mStateBuilder.build());
        mMediaSession.setCallback(new MySessionCallback());
        mMediaSession.setActive(true);
    }


    private void releasePlayer() {
        mCurrentPosition = mExoPlayer.getCurrentPosition();
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mExoPlayer != null) {
            releasePlayer();
            mMediaSession.setActive(false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String currentStepVideoUrl = currentStep.getStepVideoUrl();
        if (currentStepVideoUrl != null || !currentStepVideoUrl.isEmpty()) {
            Uri videoUri = Uri.parse(currentStepVideoUrl);
            initializePlayer(videoUri);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putParcelable(KEY_CURRENT_STEP, currentStep);
        if (mExoPlayer != null) {
            mCurrentPosition = mExoPlayer.getCurrentPosition();
        }
        currentState.putLong(KEY_VIDEO_CURRENT_POSITION, mCurrentPosition);

        super.onSaveInstanceState(currentState);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, mExoPlayer
                    .getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, mExoPlayer
                    .getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
    }

    @Override
    public void onPositionDiscontinuity() {
    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }
}
