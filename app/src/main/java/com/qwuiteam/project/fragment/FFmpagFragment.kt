package com.qwuiteam.project.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.FFprobeKit
import com.arthenica.ffmpegkit.FFprobeKit.getMediaInformation
import com.arthenica.ffmpegkit.ReturnCode
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.LogUtils
import com.qwuiteam.project.R
import com.qwuiteam.project.utils.openFile
import kotlinx.android.synthetic.main.fragment_ffmpag.*
import java.io.File


//-vn 表示不使用视频
//-ar 设置音频采样率
//-ac 设置音频 Channels
//-ab 设置音频比特率 bitrate
//-f 输出文件的格式
class FFmpagFragment : BaseFragment() {

    val watermark = "/storage/emulated/0/Download/watermark.png"
    val cover = "/storage/emulated/0/Download/Cover.png"
    val dir = "/storage/emulated/0/Download/"
    val dirImages = "/storage/emulated/0/Download/images"
    val testDir = "/storage/emulated/0/Download/test"
    val dirDownload = "/storage/emulated/0/Download"
    val videoDir = "/storage/emulated/0/Download/video"
    val videoWaterAvi = "/storage/emulated/0/Download/video/video_with_watermarkV2.avi"
    val opening = "/storage/emulated/0/Download/input.mp4"
    val episode = "/storage/emulated/0/Download/opening.mp4"
    val ending = "/storage/emulated/0/Download/result.mp4"
    val output = "/storage/emulated/0/Download/output2.mp4"
    val output1 = "/storage/emulated/0/Download/output1.mp4"
    val audio1 = "/storage/emulated/0/Download/audio1.mp3"
    val sunshine = "/storage/emulated/0/Download/video/sunshine.mp4"
    val piano_quiet = "/storage/emulated/0/Download/audio/piano_quiet.mp3"
    val class_alarm = "/storage/emulated/0/Download/audio/class_alarm.mp3"

    override fun getLayoutId(): Int = R.layout.fragment_ffmpag

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FileUtils.createOrExistsDir(testDir)
        //ffmpeg -i infile.mp4 -f srt -i infile.srt -c:v copy -c:a copy -c:s mov_text outfile.mp4
        add_subtitle.setOnClickListener {
//            val command = "-i $sunshine -f srt -i-vf subtitles=$videoDir/subtitles.srt -c copy -y $videoDir/subtitle_video2.mkv"
//            val command = "-i $sunshine -f srt -i $videoDir/subtitles.srt -c:v copy -c:a copy -c:s mov_text $videoDir/subtitle_video2.mp4"

            val subtitlePAth = "$videoDir/subtitles.srt"
            val outputVideoPath = "$videoDir/subtitle_video3.mp4"

            val cmd =
                "-i " + sunshine + " -vf subtitles=\"" + subtitlePAth.toString() + ":force_style='Alignment=10,Fontsize=18\" " +
                        outputVideoPath

//            val command = "-i $sunshine -vf srt -i $videoDir/subtitles.srt -y $videoDir/subtitle_video2.mp4"
            FFmpegKit.executeAsync(cmd) {
                if (ReturnCode.isSuccess(it.returnCode)) {
                    it.arguments.last().openFile(requireContext())
                }
            }
        }
        show_streams.setOnClickListener {
            val execute = FFprobeKit.execute("-show_streams -i $sunshine")
        }

        //ffmpeg -i INPUT -vf "split [main][tmp]; [tmp] crop=iw:ih/2:0:0, vflip [flip]; [main][flip] overlay=0:H/2" OUTPUT
        flip_video.setOnClickListener {
            val command = "-i $episode -vf \"split [main][tmp]; [tmp] crop=iw:ih/2:0:0, vflip [flip]; [main][flip] overlay=0:H/2\" -y $testDir/flip.mp4"
            FFmpegKit.executeAsync(command) {
                if (ReturnCode.isSuccess(it.returnCode)) {
                    it.arguments.last().openFile(requireContext())
                }
            }
        }
        auto_select_stream.setOnClickListener {
            //ffmpeg -i A.avi -i B.mp4 out1.mkv out2.wav -map 1:a -c:a copy out3.mov
            val command = "-i $videoWaterAvi -i $videoDir/hasBackground_keep_quality.mp4 $testDir/out1.mkv $testDir/out2.wav -map 1:a -c:a copy $testDir/out3.mov"
            FFmpegKit.executeAsync(command) {
                if (ReturnCode.isSuccess(it.returnCode)) {
                    it.arguments.last().openFile(requireContext())
                }
            }
        }
        maintain_original_quality.setOnClickListener {
            val out = "$videoDir/maintain_original_quality3.mp4"
            val command = "-y -i $sunshine -acodec copy -vcodec libx264 $out"
            FFmpegKit.executeAsync(command) {
                if (ReturnCode.isSuccess(it.returnCode)) {
                    out.openFile(requireContext())
                }
            }
        }
        addWatermark_music.setOnClickListener {
            val commands = arrayListOf<String>()
            val output1 = "$dirDownload/video/video_with_watermarkV2.mp4"
            commands.apply {
                add("-i")
                add(sunshine)
                add("-i")
                add(piano_quiet)
                add("-filter_complex")
                add("overlay=20:20")
                add(output1)
            }
            FFmpegKit.executeWithArguments(commands.toTypedArray())
            val output = "$dir/video/addWatermark_music.mp4"
            FileUtils.delete(output)
            val command =
                "-i $output1 -i $piano_quiet -map 0:v -map 1:a -b:v 4455306k -s 2560x1440 -shortest $output"
            FFmpegKit.executeAsync(command) {
                Log.d("wwq", "FFmpegSession: " + it.returnCode);
                if (ReturnCode.isSuccess(it.returnCode)) {
                    output.openFile(requireContext())
                }
            }
        }
        image_to_video.setOnClickListener {
            val output = "$dir/video/image_to_video.mp4"
            FFmpegKit.execute("-r 30 -s 1920*1080 -loop 1 -i $cover -t 3 -vcodec libx264 -crf 25 -pix_fmt yuv420p -c:a aac -y $output")
        }
        //ffmpeg -r 30 -f image2 -i im%04d.png -c:v huffyuv -pix_fmt rgb24 output.avi
        //String strCommand = "ffmpeg -loop 1 -t 3 -i " + /sdcard/videokit/1.jpg + " -loop 1 -t 3 -i " + /sdcard/videokit/2.jpg + " -loop 1 -t 3 -i " + /sdcard/videokit/3.jpg + " -loop 1 -t 3 -i " + /sdcard/videokit/4.jpg + " -filter_complex [0:v]trim=duration=3,fade=t=out:st=2.5:d=0.5[v0];[1:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v1];[2:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v2];[3:v]trim=duration=3,fade=t=in:st=0:d=0.5,fade=t=out:st=2.5:d=0.5[v3];[v0][v1][v2][v3]concat=n=4:v=1:a=0,format=yuv420p[v] -map [v] -preset ultrafast " + /sdcard/videolit/output.mp4;
//                "-r 30 -s 1920x1080 -i $dirImages/%d.png -vcodec libx264 -crf 25 -pix_fmt yuv420p -y $output")
        images_to_video.setOnClickListener {
            val output = "$dir/video/images_to_video.mp4"
            FFmpegKit.executeAsync(
            "-framerate 24 -i ${dirImages}/img%03d.png -y $output")
            {
                Log.d("wwq", "images_to_video: " + it.returnCode);
                if (ReturnCode.isSuccess(it.returnCode)) {
                    output.openFile(requireContext())
                }
            }
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
            val information = getMediaInformation(sunshine).mediaInformation
            LogUtils.d("info: " + information.allProperties)
        }

        add_cover_video.setOnClickListener {
//            FFmpegKit.execute("-i $sunshine -i $cover -map 1 -map 0 -c copy -disposition:0 attached_pic $dir/video/video_with_coverV6.mp4")
//            FFmpegKit.execute("-i $opening -i $cover -filter_complex 'overlay=10:10' $dir/video/video_with_cover.mp4")
//            ffmpeg -i in.mp4 -i IMAGE -map 0 -map 1 -c copy -c:v:1 png -disposition:v:1 attached_pic out.mp4
            //to do no working
            FFmpegKit.execute("-i $sunshine -i $cover -map 0 -map 1 -c copy -c:v:1 png -disposition:v:1 attached_pic $dir/video/video_with_coverV7.mp4")

        }

        encode_video.setOnClickListener {
            FFmpegKit.execute(
                "-i " +
                        "/storage/emulated/0/Download/input.mp4 -c:v mpeg4 /storage/emulated/0/Download/result.mp4"
            )
        }

        //合并2个视频，如果不是同一个比例，后面的会强制按前面视频比例来显示
        image_cover_merge_video.setOnClickListener {
//            val output = "$dir/video/${System.currentTimeMillis()}.avi"
//            FFmpegKit.execute("-loop 1 -i $cover -t 1 $output")
//
//            FFmpegKit.execute("-i $output -qscale:v 1 $videoDir/intermediate1.mpg")
//            FFmpegKit.execute("-i $videoWaterAvi -qscale:v 1 $videoDir/intermediate2.mpg")
//            FFmpegKit.execute("-i concat:'${videoDir}/intermediate1.mpg|${videoDir}/intermediate2.mpg' -c copy $videoDir/intermediate_all.mpg")
//            FFmpegKit.execute("-i $videoDir/intermediate_all.mpg -qscale:v 2 $videoDir/image_cover_merge_video.avi")

            //todo keep rate
            FFmpegKit.executeAsync("-i concat:'${videoDir}/intermediate1.mpg|${videoDir}/intermediate2.mpg' -c copy -y $videoDir/intermediate_all.mpg"){
                if (ReturnCode.isSuccess(it.returnCode)) {
                    it.arguments.last().openFile(requireContext())
                }
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
            File("$dirDownload/images").mkdir()
            FFmpegKit.execute(
                "-i $sunshine -vf fps=1 $dirDownload/images/%d.png"
            ).let {
                if (ReturnCode.isSuccess(it.returnCode)) {
                    Log.d("wwq", "SUCCESS: ");
                }
            }
        }
        video_convert_image.setOnClickListener {
            FFmpegKit.execute(
                "-i $opening -f image2 -ss 00:00:02 -vframes 1 -preset superfast  /storage/emulated/0/Download/resultFrame.jpg"
            ).let {
                if (ReturnCode.isSuccess(it.returnCode)) {
                    Log.d("wwq", "SUCCESS: ");
                }
            }
        }
        scale_video.setOnClickListener {
            val out = "$videoDir/${System.currentTimeMillis()}.mp4"
            FFmpegKit.executeAsync("-i $sunshine -vf scale=320:240,setsar=1:1 $out") {
                if (ReturnCode.isSuccess(it.returnCode)) {
                    out.openFile(requireContext())
                }
            }
        }
        add_background_audio.setOnClickListener {
//            val command = "-i $opening -i $audio1 -map 0:v -map 1:a $dirDownload/hasBackground.mp4"
//            FFmpegKit.execute(command)
            //add background keep quality
//            val command = "-i $sunshine -i $piano_quiet -map 0:v -c:v copy -map 1:a $videoDir/hasBackground_keep_quality.mp4"
//            FFmpegKit.executeAsync(command){
//                if (ReturnCode.isSuccess(it.returnCode)) {
//                    it.arguments.last().openFile(requireContext())
//                }
//            }
//            //keep 720p
//            val command = "-i $sunshine -i $piano_quiet -map 0:v -vf scale=-1:720 -map 1:a -shortest $videoDir/hasBackground_horizontal_720p.mp4"
//            FFmpegKit.executeAsync(command){
//                if (ReturnCode.isSuccess(it.returnCode)) {
//                    it.arguments.last().openFile(requireContext())
//                }
//            }
            //keep 1080p
            val command = "-i $opening -i $piano_quiet -map 0:v -vf scale=-1:1080 -map 1:a -shortest -y $videoDir/hasBackground_vertical_1080p.mp4"
            FFmpegKit.executeAsync(command){
                if (ReturnCode.isSuccess(it.returnCode)) {
                    it.arguments.last().openFile(requireContext())
                }
            }

        }

        //-shortest 以最短的为基准 ， -c:v copy 不进行编解码，有压缩
        add_background_audio_cy.setOnClickListener {
            val command =
                "-i $opening -i $class_alarm -map 0:v -map 1:a -c:v copy -shortest $dirDownload/hasBackgroundClassAlarm.mp4"
            FFmpegKit.execute(command)
        }

        //音频循环直到视频结束
        add_background_audio_loop.setOnClickListener {
            val command =
                "-i $sunshine -stream_loop -1 -i $class_alarm -c:v copy -map 0:v -map 1:a -shortest $dirDownload/hasBackgroundLoop.mp4"
            FFmpegKit.execute(command)
        }
        //ffmpeg -y -i 124.mp3 -vn -acodec copy -ss 00:00:00 -t 00:01:32 output.mp3
        //ss 开始时间 t持续时间
        crop.setOnClickListener {
            //audio
//            FFmpegKit.execute(
//                "-i $piano_quiet -c:a copy -ss 00:00:05 -t 00:00:30 $dirDownload/crop_piano.mp3")
            FFmpegKit.execute("-i $sunshine -c:v copy -ss 00:00:05 -t 00:00:35 $dirDownload/crop_sunshine_video.mp4")
        }

        mute_video.setOnClickListener {
            FFmpegKit.execute("-i $dirDownload/hasBackground.mp4 -c copy -an $dirDownload/muteVideo.mp4")
        }

    }
}