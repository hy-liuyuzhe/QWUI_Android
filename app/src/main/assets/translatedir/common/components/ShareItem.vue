<template>
  <van-image
    class="share-item-image-view"
    :src="shareIcon"
    v-if="showShare"
    @click="onShareClick"
  />
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import { goShare, android, getVersionCode } from "../../../common/bridge";
export default {
  name: "ShareItem",
  props: {
    shareIcon: {
      type: String,
      default: require("../assets/common_share_ic.png"),
    },
    shareUrl: {
      type: String,
      default: "https://wenext.love/",
    },
  },
  created() {
    this.checkShowShare();
  },
  data() {
    return {
      showShare: false,
    };
  },
  methods: {
    async checkShowShare() {
      let versionCode = await getVersionCode();
      let isAndroid = await isAndroid();
      if (isAndroid) {
        if (versionCode && versionCode > 131) {
          this.showShare = true;
        }
      } else {
        if (versionCode && versionCode > 51) {
          this.showShare = true;
        }
      }
    },
    async onShareClick() {
      goShare({
        deeplink:
          "wenext://wayak/web/full_screen?extra_url=" +
          encodeURIComponent(this.shareUrl),
        source: "web_share",
      });
    },
  },
};
Vue.use(VanImage);
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include image-view(share-item, 80px, 80px);
</style>
