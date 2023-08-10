<template>
  <div class="we-page">
    <div class="page-background" :style="{ background: bgColor }" />
    <div
      class="content-v-layout"
      :style="{
        height: fillContent ? '100%' : 'auto',
        background: contentBGColor,
      }"
    >
      <navigation-bar
        class="navigation-bar"
        :type="navigation.style"
        :title="navigation.title"
        :endIcons="navigation.endIcons"
        :endText="navigation.endText"
        :backgroundImage="navigation.backgroundImage"
        @on-back-click="onNavigationBackClick($event)"
        @on-end-icon-click="$emit('on-end-icon-click', $event)"
        @on-end-text-click="$emit('on-end-text-click', $event)"
      />
      <slot />
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import NavigationBar from "./NavigationBar.vue";
import { NAVIGATION_BAR_STYLE } from "../constant/index";
import { closeWindow } from "../../../common/bridge/index";

export default {
  name: "Page",
  components: {
    "navigation-bar": NavigationBar,
  },
  props: {
    bgColor: {
      type: String,
      default: "#ffffff",
    },
    contentBGColor: {
      type: String,
      default: "#ffffff",
    },
    fillContent: {
      type: Boolean,
      default: false,
    },
    navigation: {
      type: Object,
      default() {
        return {
          title: "",
          style: NAVIGATION_BAR_STYLE.DRAK,
          endIcons: [],
          endText: {
            action: String,
            text: "",
            textColor: {
              type: String,
              default: "#AAAAAA",
            },
          },
          backgroundImage: String,
        };
      },
    },
  },
  methods: {
    onNavigationBackClick() {
      if (this.$route.name === Vue.prototype.rootName) {
        closeWindow();
        return;
      }
      window.history.length > 1 ? this.$router.go(-1) : closeWindow();
    },
  },
};
</script>

<style lang="scss" scoped>
@import "@/common/css/index.scss";

@include we(page) {
  width: 100%;
  box-sizing: border-box;

  @include backgroud(page, #ffffff);

  @include v-layout(content) {
    position: absolute;
    .navigation-bar {
      position: -webkit-sticky;
      position: sticky;
      top: 0;
      z-index: 1;
    }
  }
}
</style>
