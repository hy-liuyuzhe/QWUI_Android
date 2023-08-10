<template>
  <div class="root-layout">
    <img v-if="headImage" :src="headImage" class="header-img" />
    <div
      :class="[
        headImage == undefined
          ? 'content-root-layout'
          : 'content-root-layout-half-corner',
      ]"
      :style="{ background: bgColor }"
    >
      <slot name="top-img"></slot>
      <span
        v-if="titleText"
        class="title-text-view"
        :style="{ color: titleColor == undefined ? '#222222' : titleColor }"
      >
        {{ titleText }}
      </span>
      <span
        v-if="messageText"
        class="message-text-view"
        :style="messageTextStyle"
      >
        {{ messageText }}
      </span>
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
    headImage: String,
    titleText: String,
    titleColor: String,
    messageText: String,
    messageTextStyle: Object,
    primaryButtonText: String,
    secondaryButtonText: String,
    bgColor: {
      type: String,
      default: "#ffffff",
    },
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
@import "../css/index.scss";
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
  background: #ffffff;
  border-radius: 32px;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 48px;
  .title-text-view {
    font-size: 36px;
    font-family: SFUIText-Bold, SFUIText;
    font-weight: bold;
    color: #222222;
    line-height: 48px;
    text-align: center;
  }
  .message-text-view {
    font-size: 32px;
    font-family: SFUIText-Regular, SFUIText;
    font-weight: 400;
    color: #222222;
    line-height: 48px;
    margin-top: 40px;
    white-space: pre;
    overflow-wrap: break-word;
    word-wrap: break-word;
    white-space: pre-line;
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
      background: #00e0d7;
      border-radius: 44px;
      @include compat.margin-start(0px);
    }
    .single-primary-button {
      @extend .primary-button;
      width: 100%;
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
.content-root-layout-half-corner {
  @extend .content-root-layout;
  border-radius: 0px 0px 32px 32px;
  margin-top: -1px;
  padding: 19px 48px 48px 48px;
}
</style>