<template>
  <div class="we-page">
    <div class="content-bg"></div>
    <div class="content-v-layout">
      <navigation-bar
        class="navigation-bar"
        :type="navigationStyle"
        :title="$t('couple_play_list')"
        @on-back-click="onNavBackClick($event)"
      />
      <sing-title-section
        class="section-play-list"
        :title="$t('couple_play_list')"
      >
        <div class="play-list-v-layout">
          <div
            class="play-list-item-v-layout"
            v-for="(signupInfo, index) in signupList"
            :key="index"
          >
            <van-image
              class="right-love-image-view"
              :src="require('./assets/couple_card_right_love_ic.png')"
            />
            <div class="play-list-user-info-h-layout">
              <div class="avatars-layout">
                <van-image
                  class="right-avatar-image-view"
                  :src="signupInfo.cpCouples[1].avatar"
                  @click="onAvatarClick(signupInfo.cpCouples[1].uid)"
                  round
                  error-icon="user-circle-o"
                />
                <van-image
                  class="left-avatar-image-view"
                  :src="signupInfo.cpCouples[0].avatar"
                  @click="onAvatarClick(signupInfo.cpCouples[1].uid)"
                  round
                  error-icon="user-circle-o"
                />
              </div>
              <div class="name-v-layout">
                <span class="name-text-view">{{
                  signupInfo.cpCouples[0].name
                }}</span>
                <span class="name-text-view">{{
                  signupInfo.cpCouples[1].name
                }}</span>
              </div>
            </div>
            <div class="center-line"></div>
            <div class="play-list-song-h-layout">
              <div class="song-name-text-view">
                {{ signupInfo.signupParams.songName }}
              </div>
              <van-image
                class="mic-image-view"
                :src="require('./assets/couple_mic_ic.png')"
              />
              <span class="song-singer-text-view">{{
                signupInfo.signupParams.singer
              }}</span>
            </div>
          </div>
        </div>
      </sing-title-section>
    </div>
  </div>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import { Image as VanImage, Toast } from "vant";
import { hideNavigationBar, closeWindow } from "../../../common/bridge";
import { NAVIGATION_BAR_STYLE } from "@/common/constant/index";
import NavigationBar from "@/common/components/NavigationBar";
import api from "../api/index";
import { RES_COMMON_CODE } from "../../../common/network/constant";
import SingTitleSection from "./components/SingTitleSection.vue";
import { SIGNUP_STATUS } from "../constant/index";
import { goUserProfle } from "../../../common/bridge/index";

Vue.use(VanImage);
Vue.use(Toast);
export default {
  name: "CoupleSingPlayList",
  components: {
    "navigation-bar": NavigationBar,
    "sing-title-section": SingTitleSection,
  },
  created() {
    hideNavigationBar();
    this.activityId = this.$route.query.activity_id;
    this.getCPActivitySignupInfo();
  },
  data() {
    return {
      navigationStyle: NAVIGATION_BAR_STYLE.LIGHT,
      activityId: null,
      signupList: [],
    };
  },
  methods: {
    onNavBackClick() {
      window.history.length > 1 ? this.$router.go(-1) : closeWindow();
    },
    async getCPActivitySignupInfo() {
      const { code, data } = await api.getCPActivitySignupInfo({
        activityId: this.activityId,
        filterOnline: false,
      });
      if (code !== RES_COMMON_CODE.SUCCESS) {
        Toast(this.$t("common_failed_with_errorcode", { code }));
        return;
      }
      const signupList = _.filter(data.signupList, function(o) {
        return o.signUpStatus === SIGNUP_STATUS.SIGNUP_SUCCESSED;
      });
      this.signupList = _.forEach(signupList, function(o) {
        o.signupParams = JSON.parse(o.signupParams);
      });
    },
    onAvatarClick(uid) {
      goUserProfle(uid);
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include we(page) {
  width: 750px;
  box-sizing: border-box;
  position: relative;
  .content-bg {
    width: 100%;
    height: 100%;
    background: linear-gradient(135deg, #9f8bff 0%, #dcc6fe 100%);
    backdrop-filter: blur(7px);
    position: fixed;
  }
  @include v-layout(content) {
    width: 100%;
    height: 100%;
    padding-bottom: 100px;
    .navigation-bar {
      position: fixed;
    }
    .section-play-list {
      margin-top: 216px;
      width: 690px;
      @include v-layout(play-list) {
        width: 100%;
        margin-top: 20px;
        padding-bottom: 30px;
        @include v-layout(play-list-item) {
          width: 650px;
          height: 200px;
          background: linear-gradient(136deg, #ffffff 0%, #eed8ff 100%);
          border-radius: 16px;
          margin-top: 24px;
          position: relative;
          @include image-view(right-love, 150px, 200px) {
            position: absolute;
            right: 0;
            top: 0;
            @include dir {
              left: 0;
              right: unset;
              transform: rotateY(180deg);
            }
          }
          @include h-layout(play-list-user-info) {
            width: 100%;
            margin-top: 33px;
            .avatars-layout {
              width: 104px;
              height: 62px;
              position: relative;
              @include compat.margin-start(24px);
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
                width: 266px;
              }
            }
          }
          .center-line {
            width: 610px;
            height: 1px;
            background: #b09bf1;
            opacity: 0.3;
            margin-top: 24px;
          }
          @include h-layout(play-list-song) {
            width: 100%;
            margin-top: auto;
            margin-bottom: 24px;
            @include text-view(
              song-name,
              $font-size: 30px,
              $line-height: 38px,
              $font-color: #897bf5,
              $font-weight: $--font-weight-medium
            ) {
              @include compat.margin-start(20px);
            }
            @include image-view(mic, 32px, 32px) {
              @include compat.margin-start(8px);
            }
            @include text-view(
              song-singer,
              $font-size: 28px,
              $line-height: 36px,
              $font-color: #333333,
              $font-weight: $--font-weight-regular
            ) {
              @include compat.margin-start(auto);
              @include compat.margin-end(20px);
            }
          }
        }
      }
    }
  }
}
</style>
