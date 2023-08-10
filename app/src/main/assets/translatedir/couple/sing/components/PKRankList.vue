<template>
  <div class="pk-rank-list-v-layout">
    <div
      class="pk-rank-item-v-layout"
      v-for="(roundInfo, index) in pkRounds"
      :key="index"
    >
      <van-image
        class="pk-bar-image-view"
        :src="require('../assets/couple_sing_pk_bar.png')"
      />
      <div class="pk-round-v-layout">
        <span class="pk-time-text-view">{{ pkTimeStr(roundInfo.pkTime) }}</span>
        <div class="pk-users-h-layout">
          <div
            class="pk-user-v-layout"
            v-for="(cpBoardInfo, index) in [roundInfo.cp1, roundInfo.cp2]"
            :key="index"
          >
            <van-image
              :class="[
                cpBoardInfo.cpIntimacyId === roundInfo.winner
                  ? 'pk-winner-image-view'
                  : 'pk-winner-image-view-hidden',
              ]"
              :src="require('../assets/couple_sing_pk_winner.png')"
            ></van-image>
            <div class="avatars-layout">
              <van-image
                class="right-avatar-image-view"
                :src="cpBoardInfo.cpCouples[1].avatar"
                @click="onAvatarClick(cpBoardInfo.cpCouples[1].uid)"
                round
                error-icon="user-circle-o"
              />
              <van-image
                class="left-avatar-image-view"
                :src="cpBoardInfo.cpCouples[0].avatar"
                @click="onAvatarClick(cpBoardInfo.cpCouples[0].uid)"
                round
                error-icon="user-circle-o"
              />
            </div>
            <span class="receive-diamond-text-view">{{
              $t("couple_receive_diamond", { num: cpBoardInfo.boardValue })
            }}</span>
          </div>
        </div>
      </div>
      <span class="round-title-text-view">{{
        $t("couple_round", { round: roundInfo.pkRound })
      }}</span>
    </div>
  </div>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import { Image as VanImage, Toast, Popup } from "vant";
import moment from "moment";
import { goUserProfle } from "../../../../common/bridge/index";

Vue.use(VanImage);
Vue.use(Toast);
Vue.use(Popup);
export default {
  name: "PKRankList",
  props: {
    pkRounds: [],
  },
  methods: {
    pkTimeStr(time) {
      return moment.unix(time / 1000).format("YYYY/MM/DD HH:mm");
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
@include v-layout(pk-rank-list) {
  @include v-layout(pk-rank-item) {
    width: 650px;
    position: relative;
    margin-top: 30px;
    @include text-view(
      round-title,
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
    @include v-layout(pk-round) {
      width: 100%;
      height: 316px;
      background: linear-gradient(180deg, #fcc6fe 0%, #5467eb 100%);
      border-radius: 16px;
      margin-top: 30px;
      @include text-view(
        pk-time,
        $font-size: 28px,
        $line-height: 47px,
        $font-color: $--color-white,
        $font-weight: $--font-weight-medium,
        $text-align: center
      ) {
        width: 418px;
        background: linear-gradient(
          90deg,
          rgba(255, 247, 230, 0) 0%,
          #fff7e654 49%,
          rgba(255, 247, 230, 0) 100%
        );
        margin-top: 46px;
      }
      @include h-layout(pk-users) {
        width: 531px;
        justify-content: space-between;
        margin-top: -20px;
        @include v-layout(pk-user) {
          @include image-view(pk-winner, 60px, 63px);
          .pk-winner-image-view-hidden {
            @extend .pk-winner-image-view;
            visibility: hidden;
          }
          .avatars-layout {
            width: 154px;
            height: 92px;
            position: relative;
            @include image-view(left-avatar, 92px, 92px) {
              border: 3px solid #c0b6ff;
              position: absolute;
              left: 0;
              top: 0;
              @include dir {
                right: 0;
                left: unset;
              }
            }
            @include image-view(right-avatar, 92px, 92px) {
              border: 3px solid #fba7ea;
              position: absolute;
              left: 62px;
              top: 0;
              @include dir {
                right: 62px;
                left: unset;
              }
            }
          }
          @include text-view(
            receive-diamond,
            $font-size: 24px,
            $line-height: 30px,
            $font-color: $--color-white,
            $font-weight: $--font-weight-regular
          ) {
            margin-top: 30px;
          }
        }
      }
    }
    @include image-view(pk-bar, 287px, 118px) {
      position: absolute;
      top: 151px;
      left: 182px;
    }
  }
}
</style>
