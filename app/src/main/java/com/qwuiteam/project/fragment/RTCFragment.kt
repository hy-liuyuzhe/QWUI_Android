package com.qwuiteam.project.fragment

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.qwuiteam.project.R
import io.agora.rtc.Constants
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import io.agora.rtc.models.UserInfo
import kotlinx.android.synthetic.main.rtc_fragment.*


const val TAG = "RTCFragment"

/**
 * 默认打开send socket 获取key 创建声网 加入channel为观众，
 * 当是主播进来时，加入channel成功后获取room info麦位状态，如果可说话就改为角色为主播
 *
 * token 每次都不一样
 */
class RTCFragment : BaseFragment() {

    private val engineEventHandler: IRtcEngineEventHandler = EngineEventHandler(this)
    private lateinit var rtcEngine: RtcEngine

    override fun getLayoutId(): Int = R.layout.rtc_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init.setOnClickListener {
            rtcEngine = RtcEngine.create(
                requireContext().applicationContext,
                "ac3b4725dd0641de8a9a8d953328e489", engineEventHandler
            )
            rtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)
            rtcEngine.disableVideo()
//            rtcEngine.setAudioProfile(quality, audioShowRoom)
            rtcEngine.enableAudioVolumeIndication(600, 3, true)
            rtcEngine.setDefaultAudioRoutetoSpeakerphone(true)
        }

        joinChannel.setOnClickListener {
            val res: Int =
                rtcEngine.joinChannel(
                    "005AQAoADk1RjMyOTI2MDQ3NzQxOTM2RjFCOEJDMEMyRENCMTI4MjRCNTlDOTAQAGWPO8PKekuDhK84rq+JGNP3DfJiZfkGCwAAAAAAAA==",
                    "1847", "Extra Optional Data", 10240
                )
            Log.e(TAG, "join channel: " + res)
        }
        setRoleBTR.setOnClickListener {
            val res: Int = rtcEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER)
            Log.e(TAG, "set role setRoleBTR: " + res)
        }
        setRoleAUD.setOnClickListener {
            val res: Int = rtcEngine.setClientRole(Constants.CLIENT_ROLE_AUDIENCE)
            Log.e(TAG, "set role setRoleAUD: " + res)
        }

        playMusic.setOnClickListener {
            val filePath = "/storage/emulated/0/Download/audio/piano_quiet.mp3"
            val r = rtcEngine.startAudioMixing(filePath, false, false, -1);
            Log.d("liuyuzhe", "startAudioMixing: "+r);
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rtcEngine.leaveChannel()
        RtcEngine.destroy()
    }

    class EngineEventHandler(rtcFragment: RTCFragment) : IRtcEngineEventHandler() {

        override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
            super.onJoinChannelSuccess(channel, uid, elapsed)
            Log.e(TAG, "onJoinChannelSuccess: " + uid)
        }

        override fun onLeaveChannel(stats: RtcStats) {
            super.onLeaveChannel(stats)
            Log.e(TAG, "onLeaveChannel: " + stats?.users)
        }

        override fun onUserJoined(uid: Int, elapsed: Int) {
            super.onUserJoined(uid, elapsed)
            Log.e(TAG, "onUserJoined: " + uid)
            ToastUtils.showShort("用户加入的ID：" + uid)
        }

        override fun onClientRoleChanged(oldRole: Int, newRole: Int) {
            super.onClientRoleChanged(oldRole, newRole)
            Log.e(TAG, "onClientRoleChanged: new role" + newRole)
        }

        override fun onLocalUserRegistered(uid: Int, userAccount: String?) {
            super.onLocalUserRegistered(uid, userAccount)
        }

        override fun onUserInfoUpdated(uid: Int, userInfo: UserInfo?) {
            super.onUserInfoUpdated(uid, userInfo)
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            super.onUserOffline(uid, reason)
            Log.e(TAG, "onUserOffline.uid: " + uid+", reason: $reason")
        }

        override fun onConnectionStateChanged(state: Int, reason: Int) {
            super.onConnectionStateChanged(state, reason)
            Log.e(TAG, "onConnectionStateChanged.state: $state , reason: $reason" )
        }

        override fun onConnectionInterrupted() {
            super.onConnectionInterrupted()
        }

        override fun onConnectionLost() {
            super.onConnectionLost()
            Log.e(TAG, "onConnectionLost")
        }

        override fun onConnectionBanned() {
            super.onConnectionBanned()
        }

        override fun onApiCallExecuted(error: Int, api: String?, result: String?) {
            super.onApiCallExecuted(error, api, result)
        }

        override fun onTokenPrivilegeWillExpire(token: String?) {
            super.onTokenPrivilegeWillExpire(token)
        }

        override fun onRequestToken() {
            super.onRequestToken()
        }

        override fun onMicrophoneEnabled(enabled: Boolean) {
            super.onMicrophoneEnabled(enabled)
        }

        override fun onAudioVolumeIndication(
            speakers: Array<out AudioVolumeInfo>?,
            totalVolume: Int
        ) {
            super.onAudioVolumeIndication(speakers, totalVolume)
            if (speakers.isNullOrEmpty()) return
            speakers.forEach { speaker ->
                Log.i(TAG, "onAudioVolumeIndication: " + speaker.uid + "; volume: " + speaker.volume)
            }

        }

        override fun onActiveSpeaker(uid: Int) {
            super.onActiveSpeaker(uid)
            Log.e(TAG, "onActiveSpeaker.uid: " + uid)
        }


        override fun onFirstLocalAudioFrame(elapsed: Int) {
            super.onFirstLocalAudioFrame(elapsed)
        }

        override fun onFirstLocalAudioFramePublished(elapsed: Int) {
            super.onFirstLocalAudioFramePublished(elapsed)
        }

        override fun onFirstRemoteAudioFrame(uid: Int, elapsed: Int) {
            super.onFirstRemoteAudioFrame(uid, elapsed)
        }

        override fun onVideoStopped() {
            super.onVideoStopped()
        }

        override fun onFirstLocalVideoFrame(width: Int, height: Int, elapsed: Int) {
            super.onFirstLocalVideoFrame(width, height, elapsed)
        }

        override fun onFirstLocalVideoFramePublished(elapsed: Int) {
            super.onFirstLocalVideoFramePublished(elapsed)
        }

        override fun onFirstRemoteVideoDecoded(uid: Int, width: Int, height: Int, elapsed: Int) {
            super.onFirstRemoteVideoDecoded(uid, width, height, elapsed)
        }

        override fun onFirstRemoteVideoFrame(uid: Int, width: Int, height: Int, elapsed: Int) {
            super.onFirstRemoteVideoFrame(uid, width, height, elapsed)
        }

        override fun onUserMuteAudio(uid: Int, muted: Boolean) {
            super.onUserMuteAudio(uid, muted)
        }

        override fun onUserMuteVideo(uid: Int, muted: Boolean) {
            super.onUserMuteVideo(uid, muted)
        }

        override fun onUserEnableVideo(uid: Int, enabled: Boolean) {
            super.onUserEnableVideo(uid, enabled)
        }

        override fun onUserEnableLocalVideo(uid: Int, enabled: Boolean) {
            super.onUserEnableLocalVideo(uid, enabled)
        }

        override fun onVideoSizeChanged(uid: Int, width: Int, height: Int, rotation: Int) {
            super.onVideoSizeChanged(uid, width, height, rotation)
        }

        override fun onRemoteAudioStateChanged(uid: Int, state: Int, reason: Int, elapsed: Int) {
            super.onRemoteAudioStateChanged(uid, state, reason, elapsed)
        }

        override fun onAudioPublishStateChanged(
            channel: String?,
            oldState: Int,
            newState: Int,
            elapseSinceLastState: Int
        ) {
            super.onAudioPublishStateChanged(channel, oldState, newState, elapseSinceLastState)
        }

        override fun onVideoPublishStateChanged(
            channel: String?,
            oldState: Int,
            newState: Int,
            elapseSinceLastState: Int
        ) {
            super.onVideoPublishStateChanged(channel, oldState, newState, elapseSinceLastState)
        }

        override fun onAudioSubscribeStateChanged(
            channel: String?,
            uid: Int,
            oldState: Int,
            newState: Int,
            elapseSinceLastState: Int
        ) {
            super.onAudioSubscribeStateChanged(
                channel,
                uid,
                oldState,
                newState,
                elapseSinceLastState
            )
        }

        override fun onVideoSubscribeStateChanged(
            channel: String?,
            uid: Int,
            oldState: Int,
            newState: Int,
            elapseSinceLastState: Int
        ) {
            super.onVideoSubscribeStateChanged(
                channel,
                uid,
                oldState,
                newState,
                elapseSinceLastState
            )
        }

        override fun onRemoteVideoStateChanged(uid: Int, state: Int, reason: Int, elapsed: Int) {
            super.onRemoteVideoStateChanged(uid, state, reason, elapsed)
        }

        override fun onUserSuperResolutionEnabled(uid: Int, enabled: Boolean, reason: Int) {
            super.onUserSuperResolutionEnabled(uid, enabled, reason)
        }

        override fun onChannelMediaRelayStateChanged(state: Int, code: Int) {
            super.onChannelMediaRelayStateChanged(state, code)
        }

        override fun onChannelMediaRelayEvent(code: Int) {
            super.onChannelMediaRelayEvent(code)
        }

        override fun onLocalPublishFallbackToAudioOnly(isFallbackOrRecover: Boolean) {
            super.onLocalPublishFallbackToAudioOnly(isFallbackOrRecover)
        }

        override fun onRemoteSubscribeFallbackToAudioOnly(uid: Int, isFallbackOrRecover: Boolean) {
            super.onRemoteSubscribeFallbackToAudioOnly(uid, isFallbackOrRecover)
        }

        override fun onAudioRouteChanged(routing: Int) {
            super.onAudioRouteChanged(routing)
        }

        override fun onCameraReady() {
            super.onCameraReady()
        }

        override fun onCameraFocusAreaChanged(rect: Rect?) {
            super.onCameraFocusAreaChanged(rect)
        }

        override fun onCameraExposureAreaChanged(rect: Rect?) {
            super.onCameraExposureAreaChanged(rect)
        }

        override fun onFacePositionChanged(
            imageWidth: Int,
            imageHeight: Int,
            faces: Array<out AgoraFacePositionInfo>?
        ) {
            super.onFacePositionChanged(imageWidth, imageHeight, faces)
        }

        override fun onAudioQuality(uid: Int, quality: Int, delay: Short, lost: Short) {
            super.onAudioQuality(uid, quality, delay, lost)
        }

        override fun onRtcStats(stats: RtcStats?) {
            super.onRtcStats(stats)
        }

        override fun onLastmileQuality(quality: Int) {
            super.onLastmileQuality(quality)
        }

        override fun onLastmileProbeResult(result: LastmileProbeResult?) {
            super.onLastmileProbeResult(result)
        }

        override fun onNetworkQuality(uid: Int, txQuality: Int, rxQuality: Int) {
            super.onNetworkQuality(uid, txQuality, rxQuality)
        }

        override fun onLocalVideoStats(stats: LocalVideoStats?) {
            super.onLocalVideoStats(stats)
        }

        override fun onRemoteVideoStats(stats: RemoteVideoStats?) {
            super.onRemoteVideoStats(stats)
        }

        override fun onLocalAudioStats(stats: LocalAudioStats?) {
            super.onLocalAudioStats(stats)
        }

        override fun onRemoteAudioStats(stats: RemoteAudioStats?) {
            super.onRemoteAudioStats(stats)
        }

        override fun onLocalVideoStat(sentBitrate: Int, sentFrameRate: Int) {
            super.onLocalVideoStat(sentBitrate, sentFrameRate)
        }

        override fun onRemoteVideoStat(
            uid: Int,
            delay: Int,
            receivedBitrate: Int,
            receivedFrameRate: Int
        ) {
            super.onRemoteVideoStat(uid, delay, receivedBitrate, receivedFrameRate)
        }

        override fun onRemoteAudioTransportStats(uid: Int, delay: Int, lost: Int, rxKBitRate: Int) {
            super.onRemoteAudioTransportStats(uid, delay, lost, rxKBitRate)
        }

        override fun onRemoteVideoTransportStats(uid: Int, delay: Int, lost: Int, rxKBitRate: Int) {
            super.onRemoteVideoTransportStats(uid, delay, lost, rxKBitRate)
        }

        override fun onAudioMixingStateChanged(state: Int, errorCode: Int) {
            super.onAudioMixingStateChanged(state, errorCode)
        }

        override fun onAudioMixingFinished() {
            super.onAudioMixingFinished()
        }

        override fun onAudioEffectFinished(soundId: Int) {
            super.onAudioEffectFinished(soundId)
        }

        override fun onFirstRemoteAudioDecoded(uid: Int, elapsed: Int) {
            super.onFirstRemoteAudioDecoded(uid, elapsed)
        }

        override fun onLocalAudioStateChanged(state: Int, error: Int) {
            super.onLocalAudioStateChanged(state, error)
        }

        override fun onLocalVideoStateChanged(localVideoState: Int, error: Int) {
            super.onLocalVideoStateChanged(localVideoState, error)
        }

        override fun onRtmpStreamingStateChanged(url: String?, state: Int, errCode: Int) {
            super.onRtmpStreamingStateChanged(url, state, errCode)
        }

        override fun onStreamPublished(url: String?, error: Int) {
            super.onStreamPublished(url, error)
        }

        override fun onStreamUnpublished(url: String?) {
            super.onStreamUnpublished(url)
        }

        override fun onTranscodingUpdated() {
            super.onTranscodingUpdated()
        }

        override fun onRtmpStreamingEvent(url: String?, error: Int) {
            super.onRtmpStreamingEvent(url, error)
        }

        override fun onStreamInjectedStatus(url: String?, uid: Int, status: Int) {
            super.onStreamInjectedStatus(url, uid, status)
        }

        override fun onStreamMessage(uid: Int, streamId: Int, data: ByteArray?) {
            super.onStreamMessage(uid, streamId, data)
        }

        override fun onStreamMessageError(
            uid: Int,
            streamId: Int,
            error: Int,
            missed: Int,
            cached: Int
        ) {
            super.onStreamMessageError(uid, streamId, error, missed, cached)
        }

        override fun onMediaEngineLoadSuccess() {
            super.onMediaEngineLoadSuccess()
        }

        override fun onMediaEngineStartCallSuccess() {
            super.onMediaEngineStartCallSuccess()
        }

        override fun onNetworkTypeChanged(type: Int) {
            super.onNetworkTypeChanged(type)
        }

        override fun onUploadLogResult(requestId: String?, success: Boolean, reason: Int) {
            super.onUploadLogResult(requestId, success, reason)
        }
    }


}