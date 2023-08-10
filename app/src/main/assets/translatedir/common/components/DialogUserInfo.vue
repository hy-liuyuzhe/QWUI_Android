<template>
  <div class="dialog-user-info-h-layout">
    <van-image
      class="dialog-user-avatar-image-view"
      :src="userInfo.url"
      round
      error-icon="user-circle-o"
    />
    <div class="name-v-layout">
      <span class="dialog-user-name-text-view">{{ userInfo.name }}</span>
      <span class="dialog-user-id-text-view">{{ id }}</span>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";

Vue.use(VanImage);
export default {
  name: "DialogUserInfo",
  props: {
    userInfo: Object,
    id: String,
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/text.scss" as text;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include h-layout(dialog-user-info) {
  width: 100%;
  box-sizing: border-box;
  margin: 16px 48px;
  height: 151px;
  background: #f5f7fa;
  border-radius: 24px;
  padding: 32px;
  @include image-view(dialog-user-avatar, 88px, 88px);
  @include v-layout(name, flex-start) {
    @include compat.margin-start(16px);
    @include text-view(
      dialog-user-name,
      $font-size: 32px,
      $line-height: 38px,
      $font-color: $--color-222222,
      $font-weight: $--font-weight-medium,
      $line-number: 1
    );
    @include text-view(
      dialog-user-id,
      $font-size: 26px,
      $line-height: 31px,
      $font-color: $--color-777777,
      $font-weight: $--font-weight-medium,
      $line-number: 1
    ) {
      margin-top: 12px;
    }
  }
}
</style>
