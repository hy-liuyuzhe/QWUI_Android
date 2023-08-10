<template>
  <div
    class="avatar-frame-view"
    v-bind:id="`avatar-frame-${uniqueId}`"
    :style="{ transform: 'scale(' + scaleRatio + ')' }"
  ></div>
</template>
<script>
import _ from "lodash";
import SVGA from "svgaplayerweb";

const SVGAExtraType = {
  IMAGE: 0, //图片
  TEXT: 1, //文本
};

export default {
  name: "AvatarFrameView",
  data() {
    return {
      svgaPlayer: null,
      svgaParser: null,
      scaleRatio: 1.34,
    };
  },
  props: {
    avatarFrameConfig: String,
    uniqueId: {
      type: String,
      default: "",
    },
  },
  created() {
    this.playAvatarFrame(this.avatarFrameConfig);
  },
  watch: {
    avatarFrameConfig: function (data) {
      this.playAvatarFrame(data);
    },
  },
  methods: {
    playAvatarFrame(config) {
      if (_.isEmpty(config)) {
        return;
      }
      let avatarFrame = JSON.parse(config);
      this.$nextTick(() => {
        if (this.svgaPlayer == undefined || this.svgaParser == undefined) {
          this.svgaPlayer = new SVGA.Player(`#avatar-frame-${this.uniqueId}`);
          this.svgaParser = new SVGA.Parser(`#avatar-frame-${this.uniqueId}`);
        }
        const { url, scaleRatio, svgaExtraConfigs } = avatarFrame;
        this.scaleRatio = scaleRatio;
        _.forEach(svgaExtraConfigs, function (extra) {
          if (extra.type === SVGAExtraType.IMAGE) {
            this.svgaPlayer.setImage(extra.value, extra.key);
          } else if (extra.type == SVGAExtraType.TEXT) {
            this.svgaPlayer.setText(
              {
                text: extra.value,
                family: "PingFangSC-Regular, PingFang SC",
                size: `${extra.sizePx}px`,
                color: extra.color,
              },
              extra.key
            );
          }
        });
        this.svgaParser.load(this.urlConversion(url), (videoItem) => {
          this.svgaPlayer.clearsAfterStop = true;
          this.svgaPlayer.setClipsToBounds(true);
          this.svgaPlayer.setVideoItem(videoItem);
          this.svgaPlayer.startAnimation();
        });
      });
    },
    urlConversion(path) {
      let reg =
        /^(https?:\/\/)([0-9a-z.]+)(:[0-9]+)?([/0-9a-z.]+)?(\?[0-9a-z&=]+)?(#[0-9-a-z]+)?/i;
      path = path.replace(reg, "https://$2$3$4$5$6");
      return path;
    },
  },
};
</script>
<style lang="scss" scoped>
.avatar-frame-view {
  direction: ltr;
}
</style>
