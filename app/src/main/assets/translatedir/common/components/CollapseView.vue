<template>
  <div class="collapse-v-layout">
    <div class="title-h-layout" @click="onTitleClick">
      <span class="title-text-view">{{ title }}</span>
      <van-image class="icon-image-view" :src="arrowIconSrc" />
    </div>
    <div v-if="open" class="content-v-layout">
      <div class="content-divide-line" />
      <span class="content-text-view">{{ content }}</span>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";

Vue.use(VanImage);

export default {
  props: {
    title: {
      type: String,
      default: "",
    },
    content: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      open: false,
    };
  },
  computed: {
    arrowIconSrc() {
      return this.open
        ? require("../assets/common_arrow_close_ic.png")
        : require("../assets/common_arrow_open_ic.png");
    },
  },
  methods: {
    onTitleClick() {
      this.open = !this.open;
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(collapse) {
  width: 100%;
  background-color: $--color-white;

  @include h-layout(title) {
    width: 100%;
    justify-content: space-between;
    padding: 32px;
    &:active {
      opacity: 0.8;
    }

    @include text-view(
      title,
      $font-size: 30px,
      $line-height: 36px,
      $font-color: #25252f,
      $font-weight: $--font-weight-medium
    );

    @include image-view(icon, 40px, 40px);
  }

  @include v-layout(content, flex-start) {
    width: 100%;
    padding: 0px 32px 40px 32px;

    @include divide-line(content, 686px, 1px, #e9e9ea);

    @include text-view(
      content,
      $font-size: 28px,
      $line-height: 34px,
      $font-color: $--color-777777,
      $font-weight: $--font-weight-regular
    ) {
      margin-top: 32px;
    }
  }
}
</style>