<template>
  <div class="total-rank-list-v-layout">
    <div class="total-rank-list-content-v-layout">
      <div
        class="rank-item-v-layout"
        v-for="(rankInfo, index) in rankingBoard"
        :key="index"
      >
        <div class="rank-content-h-layout">
          <template v-if="index < 3">
            <van-image
              :src="getRankingImg(index + 1)"
              class="rank-image-view"
            />
          </template>
          <template v-else>
            <span class="rank-text-view">{{ index + 1 }}</span>
          </template>
          <div class="avatars-layout">
            <van-image
              class="right-avatar-image-view"
              :src="rankInfo.cpCouples[1].avatar"
              @click="onAvatarClick(rankInfo.cpCouples[1].uid)"
              round
              error-icon="user-circle-o"
            />
            <van-image
              class="left-avatar-image-view"
              :src="rankInfo.cpCouples[0].avatar"
              @click="onAvatarClick(rankInfo.cpCouples[0].uid)"
              round
              error-icon="user-circle-o"
            />
          </div>
          <div class="name-v-layout">
            <span class="name-text-view">{{ rankInfo.cpCouples[0].name }}</span>
            <span class="name-text-view">{{ rankInfo.cpCouples[1].name }}</span>
          </div>
          <div class="diamond-v-layout">
            <span class="diamond-text-view">{{ rankInfo.boardValue }}</span>
            <span class="diamond-desc-text-view">{{
              $t("couple_total_diamond")
            }}</span>
          </div>
        </div>
        <div class="bottom-line"></div>
      </div>
    </div>
    <span class="section-title-text-view">{{ $t("couple_rank_list") }}</span>
  </div>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import { Image as VanImage, Toast, Popup } from "vant";
import { goUserProfle } from "../../../../common/bridge/index";

Vue.use(VanImage);
Vue.use(Toast);
Vue.use(Popup);
export default {
  name: "TotalRankList",
  props: {
    rankingBoard: [],
  },
  methods: {
    getRankingImg(rank) {
      return require(`../assets/couple_sing_rank_${rank}.png`);
    },
    onAvatarClick(uid) {
      goUserProfle(uid);
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/text.scss" as text;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include v-layout(total-rank-list) {
  width: 100%;
  position: relative;
  @include text-view(
    section-title,
    $font-size: 32px,
    $line-height: 60px,
    $font-color: $--color-white,
    $font-weight: $--font-weight-bold
  ) {
    width: 327px;
    height: 60px;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    margin: auto;
    text-align: center;
    background-image: url("../assets/couple_sing_section_header_small_ic.png");
    background-size: cover;
  }
  @include v-layout(total-rank-list-content) {
    width: 100%;
    margin-top: 30px;
    padding-top: 30px;
    width: 690px;
    border-radius: 8px;
    border: 2px solid #cdabf2;
    background: white;
    @include v-layout(rank-item) {
      width: 100%;
      height: 126px;
      padding: 0 30px;
      @include h-layout(rank-content) {
        width: 100%;
        height: 100%;
        position: relative;
        @include image-view(rank, 42px, 50px);
        @include text-view(
          rank,
          $font-size: 32px,
          $line-height: 50px,
          $font-color: #897bf5,
          $font-weight: $--font-weight-bold
        ) {
          width: 42px;
          height: 50px;
        }
        .avatars-layout {
          width: 104px;
          height: 62px;
          position: relative;
          @include compat.margin-start(16px);
          @include image-view(left-avatar, 62px, 62px) {
            border: 3px solid #c0b6ff;
            position: absolute;
            left: 0;
            top: 0;
            @include dir {
              right: 0;
              left: unset;
            }
          }
          @include image-view(right-avatar, 62px, 62px) {
            border: 3px solid #fba7ea;
            position: absolute;
            left: 42px;
            top: 0;
            @include dir {
              right: 42px;
              left: unset;
            }
          }
        }
        @include v-layout(name) {
          @include compat.margin-start(16px);
          @include text-view(
            name,
            $font-size: 24px,
            $line-height: 30px,
            $font-color: #333333,
            $font-weight: $--font-weight-regular,
            $line-number: 1
          ) {
            width: 250px;
          }
        }
        @include v-layout(diamond, flex-end) {
          position: absolute;
          right: 30px;
          top: 31px;
          @include dir {
            left: 30px;
            right: unset;
          }
          @include text-view(
            diamond,
            $font-size: 32px,
            $line-height: 30px,
            $font-color: #897bf5,
            $font-weight: $--font-weight-medium,
            $line-number: 1
          );
          @include text-view(
            diamond-desc,
            $font-size: 24px,
            $line-height: 30px,
            $font-color: #999999,
            $font-weight: $--font-weight-regular,
            $line-number: 1
          ) {
            margin-top: 8px;
          }
        }
      }
      .bottom-line {
        width: 630px;
        height: 1px;
        background: #b09bf1;
        opacity: 0.3;
      }
    }
  }
}
</style>
