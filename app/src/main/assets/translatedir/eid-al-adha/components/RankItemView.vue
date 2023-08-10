<template>
  <div class="rank-item-h-layout" @click="onItemClick">
    <div class="start-h-layout">
      <van-image
        v-if="isShowAvatarFrame"
        class="rank-num-image-view"
        :src="rankSrc"
      />
      <span v-else class="rank-num-text-view">{{ rankInfo.ranking }}</span>
      <div class="avatar-z-layout">
        <van-image class="avatar-image-view" :src="rankInfo.avatar" round />
        <van-image
          v-if="isShowAvatarFrame"
          class="avatar-frame-image-view"
          :src="avatarFrameSrc"
        />
      </div>
      <span class="name-text-view">{{ rankInfo.name }}</span>
    </div>
    <div class="end-h-layout">
      <span class="glod-coins-num-text-view">{{ rankInfo.coins }}</span>
      <van-image
        class="glod-coins-icon-image-view"
        :src="require('../../common/assets/common_coin_88_ic.png')"
      />
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import { goUserProfle } from "../../../common/bridge/index";

Vue.use(VanImage);

export default {
  name: "RankItemView",
  props: {
    rankInfo: {
      type: Object,
      default() {
        return {
          ranking: 0,
          name: "",
          avatar: "",
          coins: 0,
          uid: 0,
        };
      },
    },
  },
  methods: {
    onItemClick() {
      goUserProfle(this.rankInfo.uid);
    },
  },
  computed: {
    isShowAvatarFrame() {
      return this.rankInfo.ranking <= 3;
    },
    avatarFrameSrc() {
      return require(`../assets/eid_al_adha_rank_${this.rankInfo.ranking}_frame_bg.png`);
    },
    rankSrc() {
      return require(`../assets/eid_al_adha_rank_${this.rankInfo.ranking}_ic.png`);
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include h-layout(rank-item) {
  width: 686px;
  height: 128px;
  justify-content: space-between;
  @include h-layout(start) {
    @include image-view(rank-num, 36px, 36px) {
      @include compat.margin-start(17px);
      @include compat.margin-end(17px);
    }
    @include text-view(
      rank-num,
      $font-size: 32px,
      $line-height: 37px,
      $font-color: #ff4700,
      $font-weight: $--font-weight-bold,
      $text-align: center,
    ) {
      width: 70px;
    }

    @include z-layout(avatar) {
      width: 128px;
      height: 128px;

      @include image-view(avatar, 88px, 88px) {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translateX(-50%) translateY(-50%);
      }

      @include image-view(avatar-frame, 128px, 128px);
    }

    @include text-view(
      name,
      $font-size: 28px,
      $line-height: 34px,
      $font-color: #724637,
      $font-weight: $--font-weight-medium,
      $line-number: 1
    ) {
      width: 300px;
    }
  }

  @include h-layout(end) {
    @include compat.margin-end(32px);
    @include text-view(
      glod-coins-num,
      $font-size: 26px,
      $line-height: 31px,
      $font-color: #724637,
      $font-weight: $--font-weight-regular,
      $line-number: 1
    );
    @include image-view(glod-coins-icon, 28px, 28px) {
      @include compat.margin-start(4px);
    }
  }
}
</style>