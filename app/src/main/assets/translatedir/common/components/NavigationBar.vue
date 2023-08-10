<template>
  <div
    :class="[
      isDark
        ? 'dark-navigation-bar-container'
        : 'light-navigation-bar-container',
      isIOS ? 'height-176px' : 'height-138px',
    ]"
    :style="{
      backgroundImage:
        backgroundImage === undefined ? 'unset' : 'url(' + backgroundImage + ')',
        background: backgroundColor === undefined ? (isDark ? '#ffffff' : '') : backgroundColor,
    }"
  >
    <van-image
      :class="[isAr() ? 'rlt-back-icon' : 'back-icon']"
      :src="backIcon"
      @click="$emit('on-back-click')"
    />
    <span :class="[isDark ? 'dark-title-text' : 'light-title-text']">
      {{ title }}
    </span>
    <div v-if="endIcons.length > 0" class="end-icon-container">
      <van-image
        v-for="(item, index) in endIcons"
        :key="index"
        class="end-icon"
        :src="item.icon"
        :id="`end-icon-${index}`"
        @click="$emit('on-end-icon-click', item.action)"
      />
    </div>
    <span
      v-else-if="endText.text"
      class="end-op-text-view"
      @click="$emit('on-end-text-click', endText.action)"
      :style="{ color: endText.textColor }"
    >
      {{ endText.text }}
    </span>
  </div>
</template>

<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import { NAVIGATION_BAR_STYLE } from "../constant/index";
import { isIOS } from "../../../common/util/index";

Vue.use(VanImage);

export default {
  name: "NavigationBar",
  props: {
    type: {
      type: Number,
      default: NAVIGATION_BAR_STYLE.DRAK,
    },
    title: {
      type: String,
      default: "",
    },
    endIcons: {
      type: Array,
      default() {
        return []; //[{action: String, icon: String}]
      },
    },
    endText: {
      type: Object,
      default() {
        return {
          action: String,
          text: "",
          textColor: {
            type: String,
            default: "#AAAAAA",
          },
        };
      },
    },
    backgroundImage: String,
    backgroundColor: String,
  },
  computed: {
    backIcon() {
      if (this.type === NAVIGATION_BAR_STYLE.DRAK) {
        return require("../assets/navigation_back_black_ic.png");
      } else {
        return require("../assets/navigation_back_white_ic.png");
      }
    },
    isDark() {
      return this.type === NAVIGATION_BAR_STYLE.DRAK;
    },
    isIOS() {
      return isIOS;
    },
  },
  methods: {
    isAr() {
      return this.$i18n.locale === "ar";
    },
  },
};
</script>

<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

.dark-navigation-bar-container {
  @extend .navigation-bar-container;
  background: #ffffff;
}

.light-navigation-bar-container {
  @extend .navigation-bar-container;
}

.height-138px {
  padding-top: 50px;
  height: 138px;
}

.height-176px {
  padding-top: 88px;
  height: 176px;
}

.navigation-bar-container {
  width: 750px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  padding-left: 32px;
  padding-right: 32px;
  background-size: 100% 100%;
  position: -webkit-sticky;
  position: sticky;
  top: 0;
  z-index: 1;

  .back-icon {
    width: 48px;
    height: 48px;
  }

  .rlt-back-icon {
    @extend .back-icon;
    transform: rotateY(180deg);
  }

  .title-text {
    @extend .single-line-text;
    flex-grow: 1;
    height: 36px;
    font-size: 36px;
    font-family: SFUIText-Semibold, SFUIText;
    font-weight: 600;
    color: #222222;
    line-height: 36px;
    text-align: center;
  }
  .dark-title-text {
    @extend .title-text;
    color: #222222;
  }
  .light-title-text {
    @extend .title-text;
    color: #ffffff;
  }
  @include text-view(
    end-op,
    $font-size: 32px,
    $line-height: 38px,
    $font-color: $--color-AAAAAA,
    $font-weight: $--font-weight-bold,
    $line-number: 1
  );
  .end-icon-container {
    display: flex;
    flex-direction: row;
    align-items: center;
    .end-icon {
      width: 48px;
      height: 48px;
      @include compat.margin-start(16px);
    }
  }
}
</style>
