package com.example.android.myapplication.uiDetail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.myapplication.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
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
import com.squareup.picasso.Picasso;

import static android.support.constraint.Constraints.TAG;

public class VideoFragment extends Fragment implements ExoPlayer.EventListener{

    private static final String VIDEO_URI = "video_uri";
    private static final String VIDEO_DESCRIPTION = "video_description";
    private static final String RECIPE_IMAGE = "recipe_image";

    private String uri;
    private String video_description;
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private TextView mVideoDirection;
    private String mImage;
    private ImageView recipeImage;

    long playbackPosition = 0;
    int currentWindow = 0;
    boolean playWhenReady = true;

    public static VideoFragment newInstance(String uri, String description, String image){
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_URI, uri);
        bundle.putString(VIDEO_DESCRIPTION, description);
        bundle.putString(RECIPE_IMAGE, image);
        VideoFragment videoFragment = new VideoFragment();
        videoFragment.setArguments(bundle);
        return videoFragment;
    }

    private void readBundle(Bundle bundle){
        if (bundle != null) {
            uri = bundle.getString(VIDEO_URI);
            video_description = bundle.getString(VIDEO_DESCRIPTION);
            mImage = bundle.getString(RECIPE_IMAGE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.exoplayer_layout, container,
                false);
        readBundle(getArguments());
        mPlayerView = rootView.findViewById(R.id.videoView);
        mVideoDirection = rootView.findViewById(R.id.media_description);
        recipeImage = rootView.findViewById(R.id.step_image);
        if(!uri.isEmpty()){
            initializePlayer(Uri.parse(uri));
            mPlayerView.setVisibility(View.VISIBLE);
            recipeImage.setVisibility(View.GONE);
        }else {
            if(!mImage.isEmpty()){
                Picasso.with(getActivity()).load(mImage)
                        .placeholder(R.drawable.android_error)
                        .error(R.drawable.android_error).into(recipeImage);
            } else {
                recipeImage.setImageResource(R.drawable.android_error);
            }
            mPlayerView.setVisibility(View.GONE);
            recipeImage.setVisibility(View.VISIBLE);
        }
        mVideoDirection.setText(video_description);
        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
            mPlayerView.requestFocus();
            mPlayerView.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(getContext(),"Baking");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                    new DefaultDataSourceFactory(getContext(),userAgent),
                    new DefaultExtractorsFactory(),null,null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(playWhenReady);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null){
            playbackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) { }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) { }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) { }

    @Override
    public void onRepeatModeChanged(int repeatMode) { }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) { }

    @Override
    public void onPlayerError(ExoPlaybackException error) { }

    @Override
    public void onPositionDiscontinuity(int reason) { }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) { }

    @Override
    public void onSeekProcessed() { }
}
