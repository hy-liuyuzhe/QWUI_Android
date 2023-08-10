<template>
  <div class="we-page">
    <div class="content-v-layout">
      <span class="title-text-view">{{
        $t("couple_signup_success_person")
      }}</span>
      <van-pull-refresh
        class="refresh-layout"
        v-model="loading"
        @refresh="onRefresh"
      >
        <div
          :class="[
            isInviteItem(index)
              ? 'invite-signup-item-h-layout'
              : 'signup-item-h-layout',
          ]"
          v-for="(signupInfo, index) in signupList"
          :key="index"
        >
          <van-image
            class="right-love-image-view"
            :src="require('./assets/couple_sing_card_heard_small_ic.png')"
          />
          <div class="avatars-layout">
            <van-image
              class="right-avatar-image-view"
              :src="signupInfo.cpCouples[1].avatar"
              round
              error-icon="user-circle-o"
              @click="onAvatarClick(signupInfo.cpCouples[1].uid)"
            />
            <van-image
              class="left-avatar-image-view"
              :src="signupInfo.cpCouples[0].avatar"
              round
              error-icon="user-circle-o"
              @click="onAvatarClick(signupInfo.cpCouples[0].uid)"
            />
          </div>
          <div class="name-v-layout">
            <span
              :class="[
                isInviteItem(index)
                  ? 'invite-signup-name-text-view'
                  : 'signup-name-text-view',
              ]"
              >{{ signupInfo.cpCouples[0].name }}</span
            >
            <span
              :class="[
                isInviteItem(index)
                  ? 'invite-signup-name-text-view'
                  : 'signup-name-text-view',
              ]"
              >{{ signupInfo.cpCouples[1].name }}</span
            >
          </div>
          <div
            :class="[
              firstItemInvited
                ? 'invited-button-h-layout'
                : 'invite-button-h-layout',
            ]"
            v-if="isInviteItem(index)"
            :style="{
              pointerEvents: firstItemInvited ? 'none' : 'auto',
            }"
            @click="onInviteClick(signupInfo)"
          >
            <van-image
              class="mic-image-view"
              :src="require('./assets/couple_mic_white_ic.png')"
              v-if="!firstItemInvited"
            />
            <span class="invite-text-view">{{
              firstItemInvited
                ? $t("couple_invited_count_down", { second: leftTime })
                : $t("couple_invite_mic")
            }}</span>
          </div>
          <span
            :class="[
              isInviteItem(index)
                ? 'invite-signup-pk-times-text-view'
                : 'signup-pk-times-text-view',
            ]"
          >
            {{
              $t("couple_pk_times", {
                times: getPKTimes(signupInfo.cpIntimacyId),
              })
            }}
          </span>
        </div>
      </van-pull-refresh>
    </div>
    <van-popup v-model="showRoundNoEndDialog" get-container="body" round>
      <common-dialog
        :messageText="$t('couple_cant_invite')"
        :primaryButtonText="$t('couple_round_no_end')"
        @on-primary-button-click="onRoundNoEndDialogConfirmClick"
      />
    </van-popup>
  </div>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import { Image as VanImage, Toast, Popup, PullRefresh } from "vant";
import api from "../api/index";
import { getMyUserInfo } from "../../../common/bridge";
import { RES_COMMON_CODE } from "../../../common/network/constant";
import { SIGNUP_STATUS } from "../constant/index";
import CommonDialog from "@/common/components/CommonDialog";
import commonApi from "@/common/api/index";
import { goUserProfle, isUserOnMic } from "../../../common/bridge/index";

Vue.use(VanImage);
Vue.use(Toast);
Vue.use(Popup);
Vue.use(PullRefresh);
export default {
  name: "CoupleSingHostControl",
  components: {
    "common-dialog": CommonDialog,
  },
  created() {
    this.activityId = this.$route.query.activity_id;
    this.activityTag = this.$route.query.activity_tag;
    this.roomId = this.$route.query.room_id;
    this.getCPActivitySignupInfo();
    this.checkHost();
  },
  data() {
    return {
      activityId: null,
      activityTag: null,
      roomId: null,
      signupList: [],
      showRoundNoEndDialog: false,
      isHost: false,
      loading: false,
      firstItemInvited: false,
      startCountDownTime: 30,
      leftTime: 10,
      countdownTimer: null,
      cpPKRounds: null,
      invitedUidSet: null,
      invitedIntimacyId: null,
    };
  },
  methods: {
    async checkHost() {
      let myUserInfo = await getMyUserInfo();
      if (_.isNil(myUserInfo)) {
        return;
      }
      const { code, data } = await commonApi.getRoomInfo({
        roomIds: [this.roomId],
      });
      if (code === RES_COMMON_CODE.SUCCESS && !_.isEmpty(data.roomInfos)) {
        this.isHost = data.roomInfos[0].owner === myUserInfo.uid;
      }
    },
    async getCPActivitySignupInfo() {
      const { code, data } = await api.getCPActivitySignupInfo({
        activityId: this.activityId,
        filterOnline: true,
      });
      this.loading = false;
      if (code !== RES_COMMON_CODE.SUCCESS) {
        Toast(this.$t("common_failed_with_errorcode", { code }));
        return;
      }
      this.signupList = _.filter(data.signupList, function(o) {
        return o.signUpStatus === SIGNUP_STATUS.SIGNUP_SUCCESSED;
      });
      this.cpPKRounds = data.cpPKRounds;
    },
    onRefresh() {
      this.getCPActivitySignupInfo();
    },
    async onInviteClick(signupInfo) {
      const { code, data } = await api.inviteCP({
        roomId: this.roomId,
        activityId: this.activityId,
        cpIntimacyId: signupInfo.cpIntimacyId,
        activityTag: this.activityTag,
      });
      if (code === RES_COMMON_CODE.CANT_INVITE_NEWCP_TO_MIC) {
        this.showRoundNoEndDialog = true;
        return;
      } else if (code === RES_COMMON_CODE.NOT_VALID_INTIMACY) {
        Toast(this.$t("couple_no_intimacy_tip"));
        this.getCPActivitySignupInfo();
        return;
      } else if (code === RES_COMMON_CODE.SUCCESS) {
        this.startCoundDown(signupInfo);
        return;
      } else {
        let head = _.head(this.signupList);
        this.signupList = _.concat(_.drop(this.signupList), [head]);
        this.cancelCoundDown();
        Toast(this.$t("common_failed_with_errorcode", { code }));
      }
    },
    onRoundNoEndDialogConfirmClick() {
      this.showRoundNoEndDialog = false;
    },
    onAvatarClick(uid) {
      goUserProfle(uid);
    },
    isInviteItem(index) {
      return index === 0 && this.isHost;
    },
    startCoundDown(signupInfo) {
      this.cancelCoundDown();
      this.firstItemInvited = true;
      this.leftTime = this.startCountDownTime;
      this.invitedUidSet = _.map(signupInfo.cpCouples, function(o) {
        return o.uid;
      });
      this.invitedIntimacyId = signupInfo.cpIntimacyId;
      this.countdownTimer = setInterval(() => {
        this.countdown();
      }, 1000);
    },
    countdown() {
      if (this.leftTime > 0) {
        this.leftTime = this.leftTime - 1;
        this.checkInivtedCpOnMic();
      } else {
        this.cancelCoundDown();
        this.getCPActivitySignupInfo();
      }
    },
    cancelCoundDown() {
      this.firstItemInvited = false;
      if (this.countdownTimer) {
        clearInterval(this.countdownTimer);
        this.countdownTimer = null;
      }
    },
    async checkInivtedCpOnMic() {
      if (_.isEmpty(this.invitedUidSet)) {
        return;
      }
      let isOnMic = await isUserOnMic({ uidSet: this.invitedUidSet });
      if (isOnMic && this.signupList[0].cpIntimacyId === this.invitedIntimacyId) {
        this.cancelCoundDown();
      }
    },
    getPKTimes(intimacyId) {
      if (_.isNil(this.cpPKRounds[intimacyId])) {
        return 0;
      } else {
        return this.cpPKRounds[intimacyId];
      }
    },
    destroyed() {
      this.cancelCoundDown();
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
  height: 871px;
  background: white;
  border-radius: 24px 24px 0px 0px;
  position: fixed;
  @include v-layout(content) {
    width: 100%;
    height: 100%;
    @include text-view(
      title,
      $font-size: 30px,
      $line-height: 96px,
      $font-color: #222222,
      $font-weight: $--font-weight-medium,
      $text-align: center
    ) {
      width: 100%;
      height: 96px;
      border-radius: 24px 24px 0px 0px;
    }
    .refresh-layout {
      width: 690px;
      height: 100%;
    }
    @include h-layout(signup-item) {
      width: 690px;
      margin-top: 16px;
      height: 127px;
      background: linear-gradient(136deg, #fff5fa 0%, #eed8ff 100%);
      border-radius: 16px;
      position: relative;
    }
    @include h-layout(invite-signup-item) {
      margin-top: 16px;
      width: 690px;
      height: 127px;
      background: linear-gradient(136deg, #fec6f4 0%, #5467eb 100%);
      border-radius: 16px;
      border: 2px solid #ffffff;
      position: relative;
    }
    @include image-view(right-love, 200px, 127px) {
      position: absolute;
      right: 0;
      top: 0;
      @include dir {
        left: 0;
        right: unset;
        transform: rotateY(180deg);
      }
    }
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
    }
    @include text-view(
      signup-name,
      $font-size: 24px,
      $line-height: 30px,
      $font-color: #333333,
      $font-weight: $--font-weight-regular,
      $line-number: 1
    ) {
      width: 250px;
    }
    @include text-view(
      invite-signup-name,
      $font-size: 24px,
      $line-height: 30px,
      $font-color: $--color-white,
      $font-weight: $--font-weight-regular,
      $line-number: 1
    ) {
      width: 250px;
    }
    @include h-layout(invite-button) {
      height: 60px;
      background: linear-gradient(133deg, #fcaeef 0%, #2a28ff 100%);
      border-radius: 35px;
      border: 2px solid #ffffff;
      padding: 0px 24px;
      position: absolute;
      top: 52px;
      right: 24px;
      @include dir {
        left: 24px;
        right: unset;
      }
      @include image-view(mic, 32px, 32px);
      @include text-view(
        invite,
        $font-size: 24px,
        $line-height: 30px,
        $font-color: $--color-white,
        $font-weight: $--font-weight-regular
      ) {
        @include compat.margin-start(8px);
      }
      &:active {
        opacity: 0.6;
      }
    }
    .invited-button-h-layout {
      @extend .invite-button-h-layout;
      background: #6e39fd;
      border-radius: 35px;
      border: 2px solid rgba(255, 255, 255, 0.4);
    }
    @include text-view(
      signup-pk-times,
      $font-size: 20px,
      $line-height: 32px,
      $font-color: #897bf5,
      $font-weight: $--font-weight-medium,
      $line-number: 1
    ) {
      position: absolute;
      top: 46px;
      right: 24px;
      @include dir {
        left: 24px;
        right: unset;
      }
    }
    @include text-view(
      invite-signup-pk-times,
      $font-size: 20px,
      $line-height: 32px,
      $font-color: $--color-white,
      $font-weight: $--font-weight-medium,
      $line-number: 1
    ) {
      position: absolute;
      top: 10px;
      right: 24px;
      @include dir {
        left: 24px;
        right: unset;
      }
    }
  }
}
</style>
