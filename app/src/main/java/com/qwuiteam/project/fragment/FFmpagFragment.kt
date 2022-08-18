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
import com.blankj.utilcode.util.SizeUtils
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
    val font = "/storage/emulated/0/Download/font/alike.ttf"

    override fun getLayoutId(): Int = R.layout.fragment_ffmpag

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FileUtils.createOrExistsDir(testDir)
        //ffmpeg -i myvideo.mp4 -i image.png -filter_complex [0:v][1:v]overlay=5:5,drawtext=text=mytext:fontcolor=orange@1.0:fontsize=30:x=30:y=200 -c:a copy output.mp4
        drawText.setOnClickListener {
            val command =
                "-i $episode -vf drawtext=" + "fontfile=$font:text='this is dynamic text':x=(w-tw)/2:y=(h-th)/2:fontcolor=red:fontsize=${
                    SizeUtils.sp2px(16f)
                }" + " -y $videoDir/draw_text_video6.mp4"


            FFmpegKit.executeAsync(command) {
                if (ReturnCode.isSuccess(it.returnCode)) {
                    it.arguments.last().openFile(requireContext())
                }
            }
        }
        mixAudio.setOnClickListener {
            val command = "-i $class_alarm -i $piano_quiet -filter_complex \"[0:a][1:a]amerge=inputs=2[a]\" -map \"[a]\" -ac 2 ${dir}/audio/mix_audio_output.mp3"

            FFmpegKit.executeAsync(command) {
                if (ReturnCode.isSuccess(it.returnCode)) {
                    it.arguments.last().openFile(requireContext())
                }
            }
        }

        mixVideoAudio.setOnClickListener {
            val drawText = "-vf drawtext=" + "fontfile=$font:text='this is test':x=(w-tw)/2:y=(h-th)/2:fontcolor=red:fontsize=${SizeUtils.sp2px(16f)}"
            val command = "-i $piano_quiet -i /storage/emulated/0/Download/video/hasBackground4.mp4 -filter_complex '[0:a][1:a]amerge=inputs=2[a]' -map '[a]' -ac 2 -map 1:v:0 $drawText -y ${videoDir}/mix_video_audio_output.mp4"

            FFmpegKit.executeAsync(command) {
                if (ReturnCode.isSuccess(it.returnCode)) {
                    it.arguments.last().openFile(requireContext())
                }
            }
        }

        add_subtitle.setOnClickListener {
            val subtitlePAth = "$videoDir/subtitles.srt"
            val outputVideoPath = "$videoDir/subtitle_video4.mp4"

            val command =
                "-i " + sunshine + " -vf subtitles=\"" + subtitlePAth.toString() + ":force_style='Alignment=4,Fontsize=18,MarginL=5,MarginV=25\" " +
                        "-y " + outputVideoPath

//            val command = "-i $sunshine -vf subtitles='$subtitlePAth:force_style=' -i $videoDir/subtitles.srt -y $videoDir/subtitle_video4.mp4"
            FFmpegKit.executeAsync(command) {
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
            val command =
                "-i $episode -vf \"split [main][tmp]; [tmp] crop=iw:ih/2:0:0, vflip [flip]; [main][flip] overlay=0:H/2\" -y $testDir/flip.mp4"
            FFmpegKit.executeAsync(command) {
                if (ReturnCode.isSuccess(it.returnCode)) {
                    it.arguments.last().openFile(requireContext())
                }
            }
        }
        auto_select_stream.setOnClickListener {
            //ffmpeg -i A.avi -i B.mp4 out1.mkv out2.wav -map 1:a -c:a copy out3.mov
            val command =
                "-i $videoWaterAvi -i $videoDir/hasBackground_keep_quality.mp4 $testDir/out1.mkv $testDir/out2.wav -map 1:a -c:a copy $testDir/out3.mov"
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

//        ffmpeg


        images_to_video.setOnClickListener {
            val output = "$dir/video/images_6_video.mp4"
//            FFmpegKit.executeAsync(
//                "-r 30 -s 1920*1080 -i ${dirImages}/%d.png -vcodec libx264 -crf 25 -pix_fmt yuv420p -c:a aac -y $output"
//            )

//            -framerate 25 -t 124 -loop 1 -i image1
//            -framerate 25 -t 124 -loop 1 -i image2
//            -framerate 25 -t 124 -loop 1 -i image3
//            -framerate 25 -t 124 -loop 1 -i image4
//            -filter_complex "[0][1][2][3]concat=n=4"
//            -c:v libx264 -s 640x480 outputfile
            FFmpegKit.executeAsync(
                "-framerate 25 -t 5 -loop 1 -i ${dirImages}/1.png -framerate 25 -t 5 -loop 1 -i ${dirImages}/5.png -framerate 25 -t 5 -loop 1 -i ${dirImages}/11.png -filter_complex '[0][1][2]concat=n=3' -c:v libx264 -s 1280x720 ${output}"
            )


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
            FFmpegKit.execute("-i $sunshine -i $cover -map 0 -map 1 -c copy -c:v:1 png -disposition:v:1 attached_pic $dir/video/video_with_coverV8.mp4")

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
            FFmpegKit.executeAsync("-i concat:'${videoDir}/intermediate1.mpg|${videoDir}/intermediate2.mpg' -c copy -y $videoDir/intermediate_all.mpg") {
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
//            val command = "-i $opening -i $piano_quiet $videoDir/hasBackground2.mp4"
            val drawText = "-vf drawtext=" + "fontfile=$font:text='this is test':x=(w-tw)/2:y=(h-th)/2:fontcolor=red:fontsize=${SizeUtils.sp2px(16f)}"

            //val command = "-i /storage/emulated/0/Download/test/out2.wav -i /storage/emulated/0/Download/video/video_with_coverV8.mp4 -y $videoDir/hasBackground4.mp4"
            val command = "-i /storage/emulated/0/Download/video/hasBackground4.mp4 -i $piano_quiet -map 0:v:0 -map 1:a:0 $drawText -shortest -y $videoDir/hasBackground6.mp4"
            FFmpegKit.executeAsync(command) {
                if (ReturnCode.isSuccess(it.returnCode)) {
                    it.arguments.last().openFile(requireContext())
                }
            }
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
//            val command =
//                "-i $opening -i $piano_quiet -map 0:v -vf scale=-1:1080 -map 1:a -shortest -y $videoDir/hasBackground_vertical_1080p.mp4"
//            FFmpegKit.executeAsync(command) {
//                if (ReturnCode.isSuccess(it.returnCode)) {
//                    it.arguments.last().openFile(requireContext())
//                }
//            }

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
//            val command = "-i $dirDownload/hasBackground.mp4 -map 0:v:0 -y $dirDownload/muteVideo.mp4"
            val command = "-i $dirDownload/hasBackground.mp4 -an -y $dirDownload/muteVideo.mp4"
            FFmpegKit.executeAsync(command) {
                if (ReturnCode.isSuccess(it.returnCode)) {
                    it.arguments.last().openFile(requireContext())
                }
            }
        }
        mute_video_add_audio_video.setOnClickListener {
            FFmpegKit.execute("-an -i $dirDownload/hasBackgroundClassAlarm.mp4  -i $piano_quiet -map 0:v -map 1:a -c:v copy -shortest $videoDir/mute_origin_video_add_audio.mp4")
        }

    }
}