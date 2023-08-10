<template>
  <div class="party-tab-container">
    <div class="left-tab-container">
      <van-image class="left-tab-img" :src="leftTabImg"></van-image>
      <span class="tab-title" @click="onLeftTabClick">{{ this.$t("rule") }}</span>
    </div>
    <div class="right-tab-container">
      <van-image class="right-tab-img" :src="rightTabImg"></van-image>
      <span class="tab-title" @click="onRightTabClick">{{ this.$t("ranking") }}</span>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
Vue.use(VanImage);
export default {
  name: "PartyTabLayout",
  props: {
    tabIndex: Number
  },
  computed: {
    leftTabImg() {
      if (this.tabIndex == 0) {
        return require("../assets/party_tab_left_selected.png");
      } else {
        return require("../assets/party_tab_left_unselected.png");
      }
    },
    rightTabImg() {
      if (this.tabIndex == 1) {
        return require("../assets/party_tab_right_selected.png");
      } else {
        return require("../assets/party_tab_right_unselected.png");
      }
    },
  },
  methods: {
    onLeftTabClick() {
      this.$emit("on-tab-click", 0);
    },
    onRightTabClick() {
      this.$emit("on-tab-click", 1);
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
.party-tab-container {
  width: 750px;
  display: flex;
  flex-direction: row;
}
.left-tab-container {
  width: 308px;
  height: 132px;
  display: flex;
  flex-direction: column;
  @include compat.padding-start(67px);
  .left-tab-img {
    width: 284px;
    height: 132px;
    z-index: -1;
  }
}
.right-tab-container {
  width: 351px;
  height: 132px;
  display: flex;
  flex-direction: column;
  @include compat.padding-start(24px);
  .right-tab-img {
    width: 284px;
    height: 132px;
    z-index: -1;
  }
}
.tab-title {
  width: 284px;
  font-size: 40px;
  font-family: SFUIText-Heavy, SFUIText;
  font-weight: 800;
  color: #030f90;
  margin-top: -90px;
}
</style>