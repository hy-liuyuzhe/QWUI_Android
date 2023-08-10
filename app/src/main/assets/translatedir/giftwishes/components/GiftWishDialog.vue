<template>
  <div class="root-layout">
    <img v-if="headImage" :src="headImage" class="header-img" />
    <div class="content-root-layout">
      <span v-if="titleText" class="title-text-view">{{ titleText }}</span>
      <span v-if="messageText" class="message-text-view">{{
        messageText
      }}</span>
      <slot />
      <div class="button-layout">
        <span
          v-if="secondaryButtonText"
          class="secondary-button"
          @click="onSecondaryButtonClick"
        >
          {{ secondaryButtonText }}
        </span>
        <span
          v-if="primaryButtonText"
          :class="[
            secondaryButtonText ? 'primary-button' : 'single-primary-button',
          ]"
          @click="onPrimaryButtonClick"
        >
          {{ primaryButtonText }}
        </span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    titleText: String,
    titleColor: String,
    messageText: String,
    primaryButtonText: String,
    secondaryButtonText: String,
  },
  methods: {
    onPrimaryButtonClick() {
      this.$emit("on-primary-button-click");
    },
    onSecondaryButtonClick() {
      this.$emit("on-secondary-button-click");
    },
  },
};
</script>

<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
.root-layout {
  width: 606px;
  background: transparent;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.header-img {
  width: 606px;
}
.content-root-layout {
  width: 606px;
  background: linear-gradient(180deg, #fff9e6 0%, #fff8e0 100%);
  border-radius: 32px;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 48px;
  .title-text-view {
    height: 48px;
    font-size: 36px;
    font-family: SFUIText-Bold, SFUIText;
    font-weight: bold;
    color: #016b66;
    line-height: 48px;
  }
  .message-text-view {
    font-size: 32px;
    font-family: SFUIText-Regular, SFUIText;
    font-weight: 400;
    color: #016b66;
    line-height: 48px;
    margin-top: 40px;
    text-align: center;
  }
  .button-layout {
    width: 100%;
    flex-direction: row;
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 40px;
    .base-button {
      @extend .single-line-text;
      height: 88px;
      font-size: 32px;
      font-family: SFUIText-Semibold, SFUIText;
      font-weight: 600;
      line-height: 88px;
      text-align: center;
    }
    .primary-button {
      @extend .base-button;
      width: 240px;
      color: #ffffff;
      border-radius: 44px;
      @include compat.margin-start(0px);
    }
    .single-primary-button {
      @extend .primary-button;
      width: 100%;
      background-image: url("../assets/gift_wishes_dialog_btn_bg.png");
      background-size: 100% 100%;
      @include compat.margin-start(0px);
    }
    .secondary-button {
      @extend .base-button;
      width: 240px;
      color: #222222;
      background: #ffffff;
      border-radius: 44px;
      border: 1px solid #e1e3e6;
    }
  }
}
</style>
