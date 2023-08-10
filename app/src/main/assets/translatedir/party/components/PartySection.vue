<template>
  <div class="section-container">
    <div class="section-title-container">
      <img
        class="section-title-img"
        :src="require('../assets/party_section_title_bg.png')"
      />
      <span class="section-title">{{ this.title }}</span>
    </div>
    <div class="content-container">
      <slot name="content"></slot>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";

Vue.use(VanImage);
export default {
  name: "PartySection",
  props: {
    title: String,
  },
};
</script>
<style lang="scss" scoped>
.section-container {
  width: 750px;
  display: flex;
  flex-direction: column;
  align-items: center;
  .section-title-container {
    width: 380px;
    height: 110px;
    display: flex;
    flex-direction: column;
    align-items: center;
    z-index: 1;
    .section-title-img {
      width: 380px;
      height: 110px;
    }
    .section-title {
      width: 133px;
      font-size: 34px;
      font-family: SFUIText-Heavy, SFUIText;
      font-weight: 800;
      color: #ffffff;
      margin-top: -68px;
    }
  }
  .content-container {
    width: 690px;
    background: #1327aa;
    border-radius: 24px;
    border: 2px solid;
    border-image: linear-gradient(
        180deg,
        rgba(73, 75, 183, 1),
        rgba(73, 75, 183, 1)
      )
      2 2;
    margin-top: -55px;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-bottom: 40px;
  }
}
</style>