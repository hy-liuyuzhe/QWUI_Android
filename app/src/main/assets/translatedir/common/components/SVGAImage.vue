<template>
  <div class="svag-image-view" v-bind:id="`svag-image-view-${uniqueId}`"></div>
</template>
<script>
import _ from "lodash";
import SVGA from "svgaplayerweb";

export default {
  name: "SVGAImage",
  data() {
    return {
      svgaPlayer: null,
      svgaParser: null,
    };
  },
  props: {
    url: {
      type: String,
      default: "",
    },
    uniqueId: {
      type: String,
      default: "",
    },
  },
  created() {
    this.playSVGA(this.url);
  },
  watch: {
    url: function (data) {
      this.playSVGA(data);
    },
  },
  methods: {
    playSVGA(url) {
      if (_.isEmpty(url)) {
        return;
      }
      this.$nextTick(() => {
        if (this.svgaPlayer == undefined || this.svgaParser == undefined) {
          this.svgaPlayer = new SVGA.Player(`#svag-image-view-${this.uniqueId}`);
          this.svgaParser = new SVGA.Parser(`#svag-image-view-${this.uniqueId}`);
        }
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
.svag-image-view {
  direction: ltr;
}
</style>
