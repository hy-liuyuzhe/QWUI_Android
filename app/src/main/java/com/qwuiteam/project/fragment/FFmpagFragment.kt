package com.qwuiteam.project.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.ReturnCode
import com.qwuiteam.project.R
import kotlinx.android.synthetic.main.fragment_ffmpag.*


/**
 *
 */
class FFmpagFragment : BaseFragment() {

    val dir = "/storage/emulated/0/Download/"
    val opening = "/storage/emulated/0/Download/input.mp4"
    val episode = "/storage/emulated/0/Download/opening.mp4"
    val ending = "/storage/emulated/0/Download/result.mp4"
    val output = "/storage/emulated/0/Download/output2.mp4"
    val output1 = "/storage/emulated/0/Download/output1.mp4"

    override fun getLayoutId(): Int = R.layout.fragment_ffmpag

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add.setOnClickListener {
            FFmpegKit.execute(
                "-i " +
                        "/storage/emulated/0/Download/input.mp4 -c:v mpeg4 /storage/emulated/0/Download/result.mp4"
            )
        }

        array_command.setOnClickListener {
            val session = FFmpegKit.execute(
                "-i $episode -y /storage/emulated/0/Download/output.gif -i $episode -c:v mpeg4 -y $output"
            )
            if (ReturnCode.isSuccess(session.returnCode)) {
                Log.d("wwq", "SUCCESS: ");
                // SUCCESS
            }
        }
        to_gif.setOnClickListener {
            val session = FFmpegKit.execute(
                "-i " +
                        "/storage/emulated/0/Download/input.mp4 -y /storage/emulated/0/Download/result.gif"
            )
            if (ReturnCode.isSuccess(session.returnCode)) {
                Log.d("wwq", "SUCCESS: ");
                // SUCCESS
            }
        }

        merge_video.setOnClickListener {
            FFmpegKit.execute(
                "-y -f concat -safe 0 -i /storage/emulated/0/Download/files.txt -c copy /storage/emulated/0/Download/output.mp4"
            ).let {
                if (ReturnCode.isSuccess(it.returnCode)) {
                    Log.d("wwq", "SUCCESS: ");
                }
            }
        }
        video_convert_images.setOnClickListener {
            FFmpegKit.execute(
                "-i $opening -vf fps=1 $dir%d.png"
            ).let {
                if (ReturnCode.isSuccess(it.returnCode)) {
                    Log.d("wwq", "SUCCESS: ");
                }
            }
        }

        /**
         * ffmpeg -i vid_dire01_seq01.mp4 -i video_base_1080p.mp4 -i vid_dire01_seq01.mp4
         * -filter_complex "[0:v]setpts=PTS-STARTPTS,scale=1920x1080,fps=24,format=yuv420p[video0];[1:v]setpts=PTS-STARTPTS,scale=1920x1080,fps=24,format=yuv420p[video1];[2:v]setpts=PTS-STARTPTS,scale=1920x1080,fps=24,format=yuv420p[video2];[video0][0:a:0][video1][1:a:0][video2][2:a:0]concat=n=3:v=1:a=1[outv][outa]" -map [outv] -map [outa] -vsync 0 output.mp4
         */
        merge_video1.setOnClickListener {
//            todo this no working
//            val command =
//                "-i $opening -i $episode $ending -filter_complex [0:v]setpts=PTS-STARTPTS,scale=1920x1080,fps=24,format=yuv420p[video0];[1:v]setpts=PTS-STARTPTS,scale=1920x1080,fps=24,format=yuv420p[video1];[2:v]setpts=PTS-STARTPTS,scale=1920x1080,fps=24,format=yuv420p[video2];[video0][0:a:0][video1][1:a:0][video2][2:a:0]concat=n=3:v=1:a=1[outv][outa] -map [outv] -map [outa] -vsync 0 $output1"
            val command = "-i concat:$opening|$episode|$ending -c copy $output1"
            FFmpegKit.execute(command)
        }

    }
}