<template>
  <div class="search-h-layout">
    <div class="input-h-layout">
      <van-image
        class="start-icon-image-view"
        :src="require('../assets/common_search_gray_32_ic.png')"
      />
      <input
        v-model="content"
        class="input-edit-view"
        :placeholder="inputHint"
      />
      <van-image
        v-if="!isContentEmpty"
        class="clear-icon-image-view"
        :src="require('../assets/common_clear_gray_32_ic.png')"
        @click="onClearClick"
      />
    </div>
    <span
      :class="['search-text-view', isContentEmpty ? '' : 'is-enable']"
      @click="onSearchClick"
    >
      {{ $t("common_search") }}
    </span>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";

Vue.use(VanImage);

export default {
  name: "SearchView",
  props: {
    inputHint: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      content: "",
    };
  },
  methods: {
    onSearchClick() {
      if (this.isContentEmpty) {
        return;
      }

      this.$emit("on-search", this.content);
    },
    onClearClick() {
      this.content = "";
    },
  },
  computed: {
    isContentEmpty() {
      return this.content.length <= 0;
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include h-layout(search) {
  width: 100%;

  @include h-layout(input) {
    width: 100%;
    height: 72px;
    @include compat.padding-start(24px);
    @include compat.padding-end(24px);
    background: #f5f7fa;
    border-radius: 40px;
    @include image-view(start-icon, 32px, 32px);

    @include edit-view(
      input,
      $width: auto,
      $height: 100%,
      $font-size: 30px,
      $font-color: $--color-222222,
      $font-weight: $--font-weight-regular,
      $background-color: #f5f7fa
    ) {
      flex-grow: 1;
      @include compat.margin-start(16px);
      border-radius: 40px;
    }

    @include image-view(clear-icon, 32px, 32px);
  }

  @include text-view(
    search,
    $font-size: 32px,
    $line-height: 45px,
    $font-color: $--color-777777,
    $font-weight: $--font-weight-medium
  ) {
    @include compat.margin-start(24px);
    @include when(enable) {
      color: #00d4cb;
    }
  }
}
</style>