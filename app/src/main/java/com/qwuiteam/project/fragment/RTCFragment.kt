//package com.qwuiteam.project.fragment
//
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import com.blankj.utilcode.util.ToastUtils
//import com.qwuiteam.project.R
//import io.agora.rtc.Constants
//import io.agora.rtc.IRtcEngineEventHandler
//import io.agora.rtc.RtcEngine
//import kotlinx.android.synthetic.main.rtc_fragment.*
//
//
//const val TAG = "RTCFragment"
//
///**
// * 默认打开send socket 获取key 创建声网 加入channel为观众，
// * 当是主播进来时，加入channel成功后获取room info麦位状态，如果可说话就改为角色为主播
// *
// * token 每次都不一样
// */
//class RTCFragment : BaseFragment() {
//
//    private val engineEventHandler: IRtcEngineEventHandler = EngineEventHandler(this)
//    private lateinit var rtcEngine: RtcEngine
//
//    override fun getLayoutId(): Int = R.layout.rtc_fragment
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        init.setOnClickListener {
//            rtcEngine = RtcEngine.create(
//                requireContext().applicationContext,
//                "ac3b4725dd0641de8a9a8d953328e489", engineEventHandler
//            )
//            rtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)
//        }
//
//        joinChannel.setOnClickListener {
//            val res: Int =
//                rtcEngine.joinChannel("005AQAoADNFNUJGQkRDQjkwQzVEMUUwMDMwQTlDN0M1OEYzMjFCMjkzRjRFRjYQAGWPO8PKekuDhK84rq+JGNNxKJ9iafBILAAAAAAAAA==",
//                    "1847", "Extra Optional Data", 10006)
//            Log.e(TAG, "join channel: "+res)
//        }
//        setRole.setOnClickListener {
//            val res: Int = rtcEngine.setClientRole(Constants.CLIENT_ROLE_BROADCASTER)
//            Log.e(TAG, "set role: "+res)
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        rtcEngine.leaveChannel()
//        RtcEngine.destroy()
//    }
//
//    class EngineEventHandler(rtcFragment: RTCFragment) : IRtcEngineEventHandler() {
//
//        override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
//            super.onJoinChannelSuccess(channel, uid, elapsed)
//            Log.e(TAG, "onJoinChannelSuccess: "+uid)
//        }
//        override fun onLeaveChannel(stats: RtcStats) {
//            super.onLeaveChannel(stats)
//            Log.e(TAG, "onLeaveChannel: " + stats?.users)
//        }
//
//        override fun onUserJoined(uid: Int, elapsed: Int) {
//            super.onUserJoined(uid, elapsed)
//            Log.e(TAG, "onUserJoined: "+uid, )
//            ToastUtils.showShort("用户加入的ID："+uid)
//        }
//
//
//    }
//
////    private class EngineEventHandler internal constructor(manager: com.tongdaxing.xchat_core.manager.agora.AgoraRtcEngine?) :
////        IRtcEngineEventHandler() {
////        private val mReference: WeakReference<com.tongdaxing.xchat_core.manager.agora.AgoraRtcEngine?>
////        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
////            super.onJoinChannelSuccess(channel, uid, elapsed)
////            LogUtil.d("onJoinChannelSuccess: uid = $uid ---> elapsed = $elapsed ---> channel = $channel")
////            if (mReference.get() != null) {
////                mReference.get().handler.sendEmptyMessage(0)
////            }
////        }
////
////        override fun onUserJoined(uid: Int, elapsed: Int) {
////            super.onUserJoined(uid, elapsed)
////            //            if (mReference.get() != null) {
//////                mReference.get().cdnLayout(uid);
//////                mReference.get().setLiveTranscoding();
//////            }
////        }
////
////        override fun onUserOffline(uid: Int, reason: Int) {
////            super.onUserOffline(uid, reason)
////            //            if (mReference.get() != null) {
//////                mReference.get().removeCdnLayout(uid);
//////                mReference.get().setLiveTranscoding();
//////            }
////        }
////
////        override fun onLeaveChannel(stats: RtcStats) {
////            super.onLeaveChannel(stats)
////            LogUtil.d("onLeaveChannel: users = " + (stats?.users ?: ""))
////        }
////
////        override fun onActiveSpeaker(uid: Int) {
////            super.onActiveSpeaker(uid)
////        }
////
////        override fun onLastmileQuality(quality: Int) {
////            super.onLastmileQuality(quality)
////            if (quality >= 3) {
////                com.tongdaxing.xchat_core.manager.agora.AgoraRtcEngine.sendAppInnerRoomEvent(
////                    RoomEvent.RTC_ENGINE_NETWORK_BAD,
////                    RoomEvent.RTC_ENGINE_NETWORK_BAD
////                )
////            }
////        }
////
////        override fun onConnectionInterrupted() {
////            super.onConnectionInterrupted()
////            com.tongdaxing.xchat_core.manager.agora.AgoraRtcEngine.sendAppInnerRoomEvent(
////                RoomEvent.RTC_ENGINE_NETWORK_CLOSE,
////                RoomEvent.RTC_ENGINE_NETWORK_CLOSE
////            )
////        }
////
////        override fun onConnectionLost() {
////            super.onConnectionLost()
////            com.tongdaxing.xchat_core.manager.agora.AgoraRtcEngine.sendAppInnerRoomEvent(
////                RoomEvent.RTC_ENGINE_NETWORK_CLOSE,
////                RoomEvent.RTC_ENGINE_NETWORK_CLOSE
////            )
////        }
////
////        override fun onAudioVolumeIndication(speakers: Array<AudioVolumeInfo>, totalVolume: Int) {
////            super.onAudioVolumeIndication(speakers, totalVolume)
////            val manager: com.tongdaxing.xchat_core.manager.agora.AgoraRtcEngine? = mReference.get()
////            if (manager != null) {
////                val message: Message = manager.handler.obtainMessage()
////                message.what = 1
////                message.obj = speakers
////                manager.handler.sendMessage(message)
////            }
////        }
////
////        override fun onUserMuteAudio(uid: Int, muted: Boolean) {
////            super.onUserMuteAudio(uid, muted)
////            val manager: com.tongdaxing.xchat_core.manager.agora.AgoraRtcEngine? = mReference.get()
////            if (manager != null) {
////                if (muted) {
////                    val message: Message = manager.handler.obtainMessage()
////                    message.what = 2
////                    message.obj = uid
////                    manager.handler.sendMessage(message)
////                }
////            }
////        }
////
////        override fun onAudioMixingFinished() {
////            super.onAudioMixingFinished()
////            if (com.tongdaxing.xchat_core.manager.agora.AgoraRtcEngine.get().musicModifier.isValidMusicFile()) {
////                com.tongdaxing.xchat_core.manager.agora.AgoraRtcEngine.sendAppInnerRoomEvent(
////                    RoomEvent.METHOD_ON_AUDIO_MIXING_FINISHED,
////                    RoomEvent.METHOD_ON_AUDIO_MIXING_FINISHED
////                )
////            } else {
////                com.tongdaxing.xchat_core.manager.agora.AgoraRtcEngine.sendAppInnerRoomEvent(
////                    RoomEvent.METHOD_ON_AUDIO_MIXING_EFFECT_FINISHED,
////                    RoomEvent.METHOD_ON_AUDIO_MIXING_EFFECT_FINISHED
////                )
////            }
////        }
////
////        init {
////            mReference =
////                WeakReference<com.tongdaxing.xchat_core.manager.agora.AgoraRtcEngine?>(manager)
////        }
////    }
//
//}