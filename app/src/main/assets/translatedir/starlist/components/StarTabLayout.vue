<template>
  <div class="star-tab-container">
    <div class="tab_bg"></div>
    <div class="start-select-img-container">
      <img
        :class="[starTabSelected ? 'star-tab-selected' : 'rich-tab-selected']"
        :src="require('../assets/star_tab_selected_new.png')"
      />
    </div>
    <div class="tabs">
      <div class="star-tab" @click="onStarTabClick">
        <span class="star-tab-text">{{ $t("star_list") }}</span>
      </div>
      <div class="rich-tab" @click="onRichTabClick">
        <span class="rich-tab-text">{{ $t("rich_list") }}</span>
      </div>
    </div>
    <star-list-page
      class="star-list-page"
      v-bind:type="starListType"
      v-show="starTabSelected"
    ></star-list-page>
    <star-list-page
      class="star-list-page"
      v-bind:type="richListType"
      v-show="richTabSelected"
    ></star-list-page>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import StarListPage from "./StarListPage";
import { RANK_LIST_INDEX, RANK_TYPE } from "../constant/constant";

Vue.use(VanImage);
export default {
  name: "StarTabLayout",
  components: {
    "star-list-page": StarListPage,
  },
  data() {
    return {
      selectIndex: RANK_LIST_INDEX.STAR,
      starListType: RANK_TYPE.RANK_TYPE_STAR,
      richListType: RANK_TYPE.RANK_TYPE_RICH,
    };
  },
  computed: {
    starTabSelected() {
      return this.selectIndex == RANK_LIST_INDEX.STAR;
    },
    richTabSelected() {
      return this.selectIndex == RANK_LIST_INDEX.RICH;
    },
  },
  methods: {
    onStarTabClick() {
      this.selectIndex = 0;
      this.$emit("on-tab-click", this.selectIndex);
    },
    onRichTabClick() {
      this.selectIndex = 1;
      this.$emit("on-tab-click", this.selectIndex);
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
.star-tab-container {
  width: 750px;
}
.tab_bg {
  width: 564px;
  height: 112px;
  box-sizing: border-box;
  background-image: url("./../assets/star_tab_bg.png");
  background-size: 100% 100%;
  position: absolute;
  left: 0;
  right: 0;
  margin: auto;
}
.tabs {
  width: 564px;
  height: 80px;
  position: absolute;
  top: 14px;
  left: 0;
  right: 0;
  margin: auto;
  display: flex;
  flex-direction: row;
}
.star-tab {
  display: inline;
  width: 282px;
  height: 84px;
  line-height: 84px;
  margin-left: 0px;
  .star-tab-text {
    width: 122px;
    height: 38px;
    font-size: 32px;
    font-family: SFUIText-Semibold, SFUIText;
    font-weight: 600;
    color: #f1e5cd;
    line-height: 38px;
  }
}
.rich-tab {
  display: inline;
  width: 282px;
  height: 84px;
  line-height: 84px;
  top: 0;
  .rich-tab-text {
    width: 135px;
    height: 38px;
    font-size: 32px;
    font-family: SFUIText-Semibold, SFUIText;
    font-weight: 600;
    color: #f1e5cd;
    line-height: 38px;
  }
}
.start-select-img-container {
  width: 564px;
  height: 112px;
  position: absolute;
  top: 0x;
  left: 0;
  right: 0;
  margin: auto;
  display: flex;
  flex-direction: row;
}
.star-tab-selected {
  width: 284px;
  height: 112px;
  @include compat.margin-start(0px);
}
.rich-tab-selected {
  width: 284px;
  height: 112px;
  @include compat.margin-start(278px);
}
#start-list-page {
  position: absolute;
  top: 122px;
  left: 32px;
  width: 686px;
  height: 1842px;
}
</style>