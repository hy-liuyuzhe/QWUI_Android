<template>
  <div class="item-z-layout">
    <template v-if="rankingInfo.ranking <= 3">
      <van-image class="rank-medal-image-view" :src="rankMedal" />
    </template>
    <template v-else>
      <span class="rank-text-view">{{ rankingInfo.ranking }}</span>
    </template>
    <van-image
      class="rank-right-deco-image-view"
      :src="require('../assets/couple_rank_right_deco.png')"
    />
    <div class="content-h-layout">
      <div class="cp-z-layout" @click="onAvatarClick(rankingInfo.uid)">
        <van-image :src="cpLeftAf" class="cp-af-image-view" />
        <van-image
          class="cp-avatar-image-view"
          :src="rankingInfo.avatar"
          round
          error-icon="user-circle-o"
        />
        <span class="name-text-view">{{ rankingInfo.name }}</span>
      </div>
      <div
        class="cp-other-z-layout"
        @click="onAvatarClick(rankingInfo.otherUid)"
      >
        <van-image :src="cpRightAf" class="cp-af-image-view" />
        <van-image
          class="cp-avatar-image-view"
          :src="rankingInfo.otherAvatar"
          round
          error-icon="user-circle-o"
        />
        <span class="other-name-text-view">{{ rankingInfo.otherName }}</span>
      </div>
      <van-image
        class="link-line-image-view"
        :src="require('../assets/couple_rank_link_line.png')"
      />
    </div>
    <span class="exp-text-view">
      {{ rankingInfo.exp }}
    </span>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import { dispatchDeeplink } from "../../../../common/bridge/index";
import _ from "lodash";

Vue.use(VanImage);
export default {
  name: "RankItem",
  props: {
    rankingInfo: Object,
  },
  computed: {
    rankMedal() {
      return require(`../assets/couple_rank_${this.rankingInfo.ranking}_medal.png`);
    },
    cpLeftAf() {
      if (this.rankingInfo.ranking <= 3) {
        return require(`../assets/couple_rank_${this.rankingInfo.ranking}_af_left.png`);
      } else {
        return require("../assets/couple_rank_af_left.png");
      }
    },
    cpRightAf() {
      if (this.rankingInfo.ranking <= 3) {
        return require(`../assets/couple_rank_${this.rankingInfo.ranking}_af_right.png`);
      } else {
        return require("../assets/couple_rank_af_right.png");
      }
    },
  },
  methods: {
    async onAvatarClick(uid) {
      if (_.isNil(uid)) {
        return;
      }
      dispatchDeeplink({
        deeplink: `wenext://wayak/userProfile?extra_uid=${uid}&extra_tab_index=1`,
        path: "/userProfile",
      });
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include z-layout(item) {
  width: 694px;
  height: 174px;
  background: linear-gradient(90deg, #d2abfb 0%, #ac8bfe 100%);
  border-radius: 16px;
  @include image-view(rank-medal, 58px, 62px) {
    position: absolute;
    top: 0;
    left: 16px;
  }
  @include text-view(
    rank,
    $font-size: 32px,
    $line-height: 38px,
    $font-color: $--color-white,
    $font-weight: $--font-weight-bold,
    $text-align: center,
    $line-number: 1
  ) {
    position: absolute;
    top: 10px;
    left: 33px;
  }
  @include image-view(rank-right-deco, 94px, 64px) {
    position: absolute;
    top: 0;
    right: 0px;
  }
  @include h-layout(content) {
    position: absolute;
    top: 24px;
    left: 100px;
    @include z-layout(cp) {
      width: 130px;
      height: 126px;
      @include image-view(cp-af, 130px, 116px) {
        position: absolute;
        top: 0;
        left: 0;
      }
      @include image-view(cp-avatar, 74px, 74px) {
        position: absolute;
        top: 28px;
        left: 28px;
      }
      @include text-view(
        name,
        $font-size: 26px,
        $line-height: 32px,
        $font-color: $--color-white,
        $font-weight: $--font-weight-bold,
        $text-align: center,
        $line-number: 1
      ) {
        position: absolute;
        left: 5px;
        bottom: 0px;
        width: 120px;
        height: 32px;
        background: #9468ff;
        border-radius: 16px;
        padding-left: 10px;
        padding-right: 10px;
      }
    }
    .cp-other-z-layout {
      @extend .cp-z-layout;
      @include compat.margin-start(44px);
      .other-name-text-view {
        @extend .name-text-view;
        background: #fd6598;
      }
    }
    @include image-view(link-line, 60px, 46px) {
      position: absolute;
      left: 122px;
      top: 40px;
    }
  }
  @include text-view(
    exp,
    $font-size: 28px,
    $line-height: 40px,
    $font-color: #ffe4a1,
    $font-weight: $--font-weight-bold
  ) {
    position: absolute;
    top: 69px;
    right: 24px;
  }
}
</style>
