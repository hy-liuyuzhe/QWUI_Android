<template>
  <div class="sign-up-item-layout">
    <van-image
      class="sign-up-heart-image-view"
      :src="require('../assets/couple_sing_signup_info_bg_heart_ic.png')"
    />
    <div class="sign-up-item-v-layout">
      <div class="avatars-layout">
        <van-image
          class="right-avatar-image-view"
          :src="signupInfo.cpCouples[1].avatar"
          @click="onAvatarClick(signupInfo.cpCouples[1].uid)"
          round
          error-icon="user-circle-o"
        />
        <van-image
          class="left-avatar-image-view"
          :src="signupInfo.cpCouples[0].avatar"
          @click="onAvatarClick(signupInfo.cpCouples[0].uid)"
          round
          error-icon="user-circle-o"
        />
      </div>
      <div class="name-h-layout">
        <span class="left-name-text-view">{{
          signupInfo.cpCouples[0].name
        }}</span>
        <span class="right-name-text-view">{{
          signupInfo.cpCouples[1].name
        }}</span>
      </div>
      <div class="song-v-layout">
        <span class="song-name-text-view">{{ signupParam.songName }}</span>
        <span class="singer-name-text-view">{{
          $t("couple_singer_name", { name: signupParam.singer })
        }}</span>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import _ from "lodash";
import { goUserProfle } from "../../../../common/bridge/index";

Vue.use(VanImage);
export default {
  name: "SignupItem",
  props: {
    signupInfo: Object,
  },
  computed: {
    signupParam() {
      return JSON.parse(this.signupInfo.signupParams);
    },
  },
  methods: {
    onAvatarClick(uid) {
      goUserProfle(uid);
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
.sign-up-item-layout {
  width: 650px;
  height: 353px;
  position: relative;
  @include image-view(sign-up-heart, 486px, 334px) {
    position: absolute;
    left: 84px;
    top: 0px;
  }
}
@include v-layout(sign-up-item) {
  width: 650px;
  height: 353px;
  background: linear-gradient(135deg, #ddc6fe 0%, #5467eb 100%);
  border-radius: 16px;
  @include text-view(
    reward-intimacy,
    $font-size: 20px,
    $line-height: 28px,
    $font-color: $--color-white,
    $font-weight: $--font-weight-bold,
    $text-align: center,
    $line-number: 1
  ) {
    width: 100%;
  }
  .avatars-layout {
    margin-top: 25px;
    width: 134px;
    height: 80px;
    position: relative;
    @include image-view(left-avatar, 80px, 80px) {
      border: 3px solid #c0b6ff;
      position: absolute;
      left: 0;
      top: 0;
      @include dir {
        right: 0;
        left: unset;
      }
    }
    @include image-view(right-avatar, 80px, 80px) {
      border: 3px solid #fba7ea;
      position: absolute;
      left: 54px;
      top: 0;
      @include dir {
        right: 54px;
        left: unset;
      }
    }
  }
  @include h-layout(name) {
    margin-top: 25px;
    @include text-view(
      left-name,
      $font-size: 28px,
      $line-height: 30px,
      $font-color: $--color-white,
      $font-weight: $--font-weight-normal,
      $text-align: center,
      $line-number: 1
    );
    .right-name-text-view {
      @extend .left-name-text-view;
      @include compat.margin-start(30px);
    }
  }
  @include v-layout(song) {
    width: 590px;
    height: 120px;
    background: rgba(255, 255, 255, 0.2);
    border-radius: 8px;
    backdrop-filter: blur(15px);
    margin-top: 30px;
  }
  @include text-view(
    song-name,
    $font-size: 32px,
    $line-height: 34px,
    $font-color: $--color-white,
    $font-weight: $--font-weight-medium,
    $text-align: center,
    $line-number: 1
  ) {
    width: 100%;
    margin-top: 24px;
  }
  @include text-view(
    singer-name,
    $font-size: 24px,
    $line-height: 26px,
    $font-color: #ffffffb3,
    $font-weight: $--font-weight-regular,
    $text-align: center,
    $line-number: 1
  ) {
    width: 100%;
    margin-top: 16px;
  }
}
</style>
