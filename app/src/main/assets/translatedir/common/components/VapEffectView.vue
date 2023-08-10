<template>
  <div class="vap-view" ref="vapEffectView">
    <van-image class="static-image-view" :src="staticUrl" v-if="vap === null" />
  </div>
</template>
<script>
import _ from "lodash";
import Vap from "video-animation-player";
import Vue from "vue";
import { Image as VanImage } from "vant";

Vue.use(VanImage);
export default {
  name: "VapEffectView",
  data() {
    return {
      vap: null,
      playError: false,
    };
  },
  props: {
    url: {
      type: String,
      default: "",
    },
    staticUrl: {
      type: String,
      default: "",
    },
    widthRatio: {
      type: Number,
      default: 0,
    },
    heightRatio: {
      type: Number,
      default: 0,
    },
    type: {
      type: Number,
      default: 0,
    },
  },
  created() {
    this.playVap(this.url);
  },
  watch: {
    url: function (data) {
      this.playVap(data);
    },
  },
  methods: {
    playVap(url) {
      if (_.isEmpty(url)) {
        return;
      }
      this.$nextTick(() => {
        this.vap = new Vap(
          Object.assign(
            {},
            {
              container: this.$refs.vapEffectView,
              src: this.urlConversion(url),
              config: {
                info: {
                  v: 2,
                  f: 24,
                  w: 436,
                  h: 436,
                  fps: 12,
                  videoW: 880,
                  videoH: 448,
                  aFrame: [440, 0, 436, 436],
                  rgbFrame: [0, 0, 436, 436],
                  isVapx: 0,
                  orien: 0,
                },
              },
              width: window.screen.width * this.widthRatio,
              height: window.screen.width * this.heightRatio,
              loop: true,
              fps: 12,
              type: this.type,
            }
          )
        );
        this.vap.on("error", () => {
          this.vap = null;
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
  destroyed() {
    if (this.vap) {
      this.vap.destroy();
    }
  },
};
</script>
<style lang="scss" scoped>
.vap-view {
  direction: ltr;
}
.static-image-view {
  width: 100%;
  height: 100%;
}
</style>
