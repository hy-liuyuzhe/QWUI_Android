package com.qwuiteam.project.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.FFprobeKit.getMediaInformation
import com.arthenica.ffmpegkit.ReturnCode
import com.blankj.utilcode.util.LogUtils
import com.qwuiteam.project.R
import kotlinx.android.synthetic.main.fragment_ffmpag.*


/**
 *
 */
class FFmpagFragment : BaseFragment() {

    val cover = "/storage/emulated/0/Download/Cover.jpg"
    val watermark = "/storage/emulated/0/Download/watermark.png"
    val dir = "/storage/emulated/0/Download/"
    val sunshine = "/storage/emulated/0/Download/video/sunshine.mp4"
    val opening = "/storage/emulated/0/Download/input.mp4"
    val episode = "/storage/emulated/0/Download/opening.mp4"
    val ending = "/storage/emulated/0/Download/result.mp4"
    val output = "/storage/emulated/0/Download/output2.mp4"
    val output1 = "/storage/emulated/0/Download/output1.mp4"

    override fun getLayoutId(): Int = R.layout.fragment_ffmpag

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //./s/ffmpeg -loop 1 -i /files/v/smallest_bunny_1080p_30fps_001.jpg -c:v libx264 -pix_fmt yuv420p -t 10 /files/v/smallest_bunny_1080p_30fps_frame_001.mp4
        image_to_video.setOnClickListener {
            val output = "$dir/video/image_to_video.mp4"
            FFmpegKit.execute("-loop 1 -i $cover -t 3 $output")
        }

        video_watermark.setOnClickListener {
            val commands = arrayListOf<String>()
            commands.apply {
                add("-i")
                add("$dir/video/sunshine.mp4")
                add("-i")
                add(watermark)
                add("-filter_complex")
                add("overlay=10:10")
                add("$dir/video/video_with_watermark.mp4")
            }
            FFmpegKit.executeWithArguments(commands.toTypedArray())
        }

        //比特率 * 时间(s) = file size (kb) * 8
        //比特率 * duration /1024/1024 / 8 = file zie (mb)
        media_info.setOnClickListener {
            val information = getMediaInformation(opening).mediaInformation
            LogUtils.d("info: " + information.allProperties)
        }

        //ffmpeg -i video.mp4 -i image.png -map 1 -map 0 -c copy -disposition:0 attached_pic out.mp4
        add_cover_video.setOnClickListener {
            //FFmpegKit.execute("-i $opening -i $cover -map 1 -map 0 -c copy -disposition:0 attached_pic $dir/video/video_with_cover.mp4")
            FFmpegKit.execute("-i $opening -i $cover -filter_complex 'overlay=10:10' $dir/video/video_with_cover.mp4")
        }

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
            val command = "-i concat:$opening|$episode|$ending -c copy $cover"
            FFmpegKit.execute(command)
        }

    }
}