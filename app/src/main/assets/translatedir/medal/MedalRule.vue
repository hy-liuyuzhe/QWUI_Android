<template>
  <div class="we-page">
    <van-image class="background-image-view" :src="backgroundImgSrc" />
    <div class="content-v-layout">
      <navigation-bar
        :type="navigationStyle"
        :title="$t('medal_rule_title')"
        @on-back-click="onNavBackClick($event)"
      />
      <item-layout class="margin-top-39px" :title="$t('medal_rule_title')">
        <div class="rule-v-layout">
          <span class="rule-title-text-view">
            {{ $t("rule_title_1") }}
          </span>
          <span class="rule-content-text-view">
            {{ $t("rule_content_1_1") }}
          </span>
          <span class="rule-content-text-view">
            {{ $t("rule_content_1_2") }}
          </span>
        </div>

        <div class="rule-v-layout">
          <span class="rule-title-text-view">
            {{ $t("rule_title_2") }}
          </span>
          <span class="rule-content-text-view">
            {{ $t("rule_content_2") }}
          </span>
        </div>

        <div class="rule-v-layout">
          <span class="rule-title-text-view">
            {{ $t("rule_title_3") }}
          </span>
          <span class="rule-content-text-view">
            {{ $t("rule_content_3") }}
          </span>
        </div>

        <div class="rule-v-layout">
          <span class="rule-title-text-view">
            {{ $t("rule_title_4") }}
          </span>
          <span class="rule-content-text-view">
            {{ $t("rule_content_4") }}
          </span>
        </div>

        <div class="rule-v-layout">
          <span class="rule-title-text-view">
            {{ $t("rule_title_5") }}
          </span>
          <span class="rule-content-text-view">
            {{ $t("rule_content_5") }}
          </span>
        </div>

        <div class="rule-v-layout">
          <span class="rule-title-text-view">
            {{ $t("rule_title_6") }}
          </span>
          <span class="rule-content-text-view">
            {{ $t("rule_content_6") }}
          </span>
        </div>

        <div class="rule-v-layout">
          <span class="rule-title-text-view">
            {{ $t("rule_title_7") }}
          </span>
          <span class="rule-content-text-view">
            {{ $t("rule_content_7") }}
          </span>
        </div>

        <div class="rule-v-layout">
          <span class="rule-title-text-view">
            {{ $t("rule_title_8") }}
          </span>
          <span class="rule-content-text-view">
            {{ $t("rule_content_8") }}
          </span>
        </div>

        <div class="rule-v-layout">
          <span class="rule-title-text-view">
            {{ $t("rule_title_9") }}
          </span>
          <span class="rule-content-text-view">
            {{ $t("rule_content_9_1") }}
          </span>
          <span class="rule-content-text-view">
            {{ $t("rule_content_9_2") }}
          </span>
          <span class="rule-content-text-view">
            {{ $t("rule_content_9_3") }}
          </span>
          <span class="rule-content-text-view">
            {{ $t("rule_content_9_4") }}
          </span>
          <span class="rule-content-text-view">
            {{ $t("rule_content_9_5") }}
          </span>
        </div>
      </item-layout>
      <div class="height-81px" />
    </div>
  </div>
</template>

<script>
import _ from "lodash";
import Vue from "vue";
import { Image as VanImage } from "vant";
import ItemLayout from "./components/ItemLayout";
import { hideNavigationBar, closeWindow } from "../../common/bridge";
import NavigationBar from "@/common/components/NavigationBar";
import { NAVIGATION_BAR_STYLE } from "@/common/constant/index";

Vue.use(VanImage);

export default {
  name: "MedalRule",
  components: {
    "navigation-bar": NavigationBar,
    "item-layout": ItemLayout,
  },
  data() {
    return {
      navigationStyle: NAVIGATION_BAR_STYLE.LIGHT,
    }
  },
  created() {
    hideNavigationBar();
  },
  computed: {
    backgroundImgSrc() {
      return require("./assets/medal_rule_bg.png");
    },
  },
  methods: {
    onNavBackClick() {
      closeWindow()
    },
  }
}
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include we(page) {
  width: 100%;
  height: auto;
  background: #180F08;
  position: relative;

  @include image-view(background, 100%, auto) {
    position: absolute;
    top: 0;
    left: 0;
    object-fit: cover;
  }

  @include v-layout(content) {
    widows: 100%;

    .margin-top-39px {
      margin-top: 39px;
    }

    .height-81px {
      widows: 100%;
      height: 81px;
    }

    @include v-layout(rule) {
      width: 100%;
      margin-top: 40px;

      @include text-view(rule-title,
        $font-size: 28px,
        $line-height: 44px,
        $font-color: #F5DDA2,
        $font-weight: $--font-weight-regular ) {
        width: 100%;
        margin-top: 16px;
      }

      @include text-view(rule-content,
        $font-size: 28px,
        $line-height: 44px,
        $font-color: #F5DDA2,
        $font-weight: $--font-weight-regular ) {
        width: 100%;
      }
    }

  }

}
</style>