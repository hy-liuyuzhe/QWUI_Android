<template>
  <div :class="bgClass" v-show="showLevel">
      <van-image class="level-icon" :src="levelIcon"></van-image>
      <span class="level-text">{{ $t("common_level", { level: level }) }}</span>
      <div class="space"></div>
    </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";

Vue.use(VanImage);
export default {
  name: "UserLevelItem",
  props: {
    level: Number,
  },
  computed: {
    showLevel() {
      return this.level && this.level > 0;
    },
    bgClass() {
      if (this.level < 10) {
        return "bg1";
      } else if (this.level < 20) {
        return "bg2";
      } else if (this.level < 30) {
        return "bg3";
      } else if (this.level < 40) {
        return "bg4";
      } else if (this.level < 50) {
        return "bg5";
      } else {
        return "bg6";
      }
    },
    levelIcon() {
      if (this.level < 10) {
        return require("../assets/level_1_to_9_ic.png");
      } else if (this.level < 20) {
        return require("../assets/level_10_to_19_ic.png");
      } else if (this.level < 30) {
        return require("../assets/level_20_to_29_ic.png");
      } else if (this.level < 40) {
        return require("../assets/level_30_to_39_ic.png");
      } else if (this.level < 50) {
        return require("../assets/level_40_to_49_ic.png");
      } else {
        return require("../assets/level_50_to_100_ic.png");
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.bg {
  height: 30px;
  display: flex;
  flex-direction: row;
  align-items: center;
}
.bg1 {
  @extend .bg;
  background: #b3df5c;
  border-radius: 16px;
}
.bg2 {
  @extend .bg;
  background: #28caa6;
  border-radius: 16px;
}
.bg3 {
  @extend .bg;
  background: #4d91ff;
  border-radius: 16px;
}
.bg4 {
  @extend .bg;
  background: #da45ff;
  border-radius: 16px;
}
.bg5 {
  @extend .bg;
  background: #ff3c8c;
  border-radius: 16px;
}
.bg6 {
  @extend .bg;
  background: linear-gradient(90deg, #fb7a00 0%, #ffc119 100%);
  border-radius: 16px;
}
.level-icon {
  width: 30px;
  height: 30px;
}
.level-text {
  font-size: 20px;
  font-family: SFUIText-Semibold, SFUIText;
  font-weight: 600;
  color: #ffffff;
}
.space {
  width: 13px;
  height: 30px
}
</style>