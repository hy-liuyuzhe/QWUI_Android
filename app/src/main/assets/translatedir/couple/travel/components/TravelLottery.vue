<template>
  <div class="travel-lottery-v-layout">
    <div class="activity-gift-z-layout">
      <van-image
        class="gift-top-bg-image-view"
        :src="require('../assets/couple_travel_gift_top_deco.png')"
      />
      <span class="activity-gift-text-view">{{
        $t("couple_activity_gift")
      }}</span>
      <van-image
        class="gift-stage-image-view"
        :src="require('../assets/couple_travel_gift_stage.png')"
      />
      <van-image class="gift-icon-image-view" :src="activityGiftIcon" />
    </div>
    <div class="lottery-hearts-h-layout">
      <div
        class="heart-item-z-layout"
        v-for="(lotteryInfo, index) in lotteryInfos"
        :key="index"
        @click="onLotteryClick(index, lotteryInfo)"
      >
        <div :class="getLotteryHeartClassName(lotteryInfo.status)"></div>
      </div>
    </div>
    <div class="bottom-v-layout">
      <span class="lottery-left-count-text-view">{{
        $t("couple_lottery_left_count", { count: lotteryNum })
      }}</span>
      <span class="get-lottery-count-opportunity-text-view">{{
        $t("couple_get_lottery_count_opportunity")
      }}</span>
    </div>
    <van-popup
      v-model="showTipDialog"
      get-container="body"
      round
      :close-on-click-overlay="false"
    >
      <travel-dialog
        :title="tipDialogTitle"
        :message="tipDialogMessage"
        :isEnd="activityEnd"
        @on-close-click="onTipDialogCloseClick"
      >
        <div class="reward-ticket-z-layout" v-if="currentShowRewardInfo">
          <van-image
            class="reward-ticket-image-view"
            :src="currentShowRewardInfo.ticketImg"
          />
          <span class="romatic-travel-text-view">{{
            $t("couple_romantic_travel")
          }}</span>
          <span class="ticket-destination-text-view">{{
            currentShowRewardInfo.name
          }}</span>
        </div>
      </travel-dialog>
    </van-popup>
  </div>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import { stat } from "../../../../common/bridge";
import { Image as VanImage, Toast, Popup } from "vant";
import { LOTTERY_HEART_STATUS } from "../../constant/index";
import api from "../../api/index";
import { RES_COMMON_CODE } from "../../../../common/network/constant";
import TravelDialog from "./TravelDialog.vue";

Vue.use(VanImage);
Vue.use(Toast);
Vue.use(Popup);
export default {
  name: "TravelLottery",
  props: {
    travelActivityInfo: Object,
    myUserInfo: Object,
  },
  data() {
    return {
      showTipDialog: false,
      tipDialogTitle: null,
      tipDialogMessage: null,
      lotteryInfos: this.travelActivityInfo.lotteryInfos,
      intimacyId: this.travelActivityInfo.intimacyId,
      lotteryNum: this.travelActivityInfo.lotteryNum,
      currentShowRewardInfo: null,
      activityGifts: this.travelActivityInfo.giftInfos,
      drawOnceNum: 0,
      isCollectFinish: false,
      unreadDialogQueue: [],
      pollResultsTimer: null,
      checkedRewardInfo: false,
    };
  },
  watch: {
    travelActivityInfo: function(data) {
      if (data) {
        this.lotteryNum = data.lotteryNum;
        this.lotteryInfos = data.lotteryInfos;
        this.intimacyId = data.intimacyId;
        this.activityGifts = data.giftInfos;
        var drawOnceNum = 0;
        _.forEach(data.lotteryInfos, function(o) {
          if (o.status === 1) {
            drawOnceNum++;
          }
        });
        let unfinishedIndex = _.findIndex(data.rewardInfos, function(o) {
          return o.status === 0;
        });
        this.isCollectFinish = unfinishedIndex === -1;
        this.drawOnceNum = drawOnceNum;
        if (!this.checkedRewardInfo) {
          this.checkedRewardInfo = true;
          this.getCpTravelRewardInfo();
        }
      }
    },
  },
  components: {
    "travel-dialog": TravelDialog,
  },
  created() {
    this.startPollRewardInfo();
  },
  computed: {
    activityEnd() {
      var timestamp = new Date().getTime();
      if (timestamp > this.travelActivityInfo.endTime) {
        return true;
      }
      return false;
    },
    activityGiftIcon() {
      if (_.isEmpty(this.activityGifts)) {
        return "";
      } else {
        return this.activityGifts[0].img;
      }
    },
  },
  methods: {
    getLotteryHeartClassName(status) {
      if (status === LOTTERY_HEART_STATUS.DRAW_ONCE) {
        return "clicked-heart-image-view";
      } else if (status == LOTTERY_HEART_STATUS.OPENED) {
        return "broken-heart-image-view";
      } else {
        return "heart-image-view";
      }
    },
    async getCpTravelActivityInfo() {
      const { data, code } = await api.getCpTravelActivityInfo();
      if (code === RES_COMMON_CODE.SUCCESS) {
        this.lotteryNum = data.lotteryNum;
        this.lotteryInfos = data.lotteryInfos;
        this.intimacyId = data.intimacyId;
      }
    },
    startPollRewardInfo() {
      this.pollResultsTimer = setInterval(() => {
        this.getCpTravelRewardInfo();
      }, 5000);
    },
    cancelPollRewardInfo() {
      if (this.pollResultsTimer) {
        clearInterval(this.pollResultsTimer);
        this.pollResultsTimer = null;
      }
    },
    async getCpTravelRewardInfo() {
      if (_.isNil(this.intimacyId)) {
        return;
      }
      const { data, code } = await api.getCpTravelRewardInfo();
      if (code === RES_COMMON_CODE.SUCCESS && !_.isNil(data)) {
        var getChanceDialogInfo;
        if (!_.isNil(data.expInfo)) {
          getChanceDialogInfo = {
            title: this.$t("couple_congratulation"),
            message: this.$t("couple_chance_add", {
              add: data.expInfo.addedExp,
              times: data.expInfo.addedNum,
            }),
            isLotteryChanceDialog: true,
          };
        }
        if (!_.isNil(data.rewardInfos)) {
          this.refreshLotteryInfo();
          let self = this;
          _.forEach(data.rewardInfos, function(o) {
            if (o.id === 0) {
              self.unreadDialogQueue.push({
                message: self.$t("couple_draw_lose_tip"),
                win: false,
              });
            } else {
              self.unreadDialogQueue.push({
                title: self.$t("couple_congratulation"),
                message: self.$t("couple_draw_success_tip", {
                  name: o.name,
                }),
                win: true,
                result: {
                  ticketImg: o.ticketImg,
                  name: o.name,
                },
              });
            }
          });
        }
        if (!_.isNil(getChanceDialogInfo)) {
          this.unreadDialogQueue.push(getChanceDialogInfo);
        }
        this.showNextRewardDialog();
      }
    },
    showNextRewardDialog() {
      if (_.isEmpty(this.unreadDialogQueue)) {
        return;
      }
      if (this.showTipDialog) {
        return;
      }
      let dialogData = this.unreadDialogQueue[0];
      if (dialogData.isLotteryChanceDialog) {
        //上报抽奖机会弹窗展示事件
        if (!_.isEmpty(this.myUserInfo)) {
          stat({
            event_id: "cp_travel",
            events: {
              uid: this.myUserInfo.uid,
              action: "lottery_change_dialog_show",
            },
          });
        }
      }
      this.unreadDialogQueue = _.drop(this.unreadDialogQueue);
      this.showTravelDialog(dialogData);
    },
    refreshLotteryInfo() {
      this.$emit("on-refresh-info");
    },
    showTravelDialog(data) {
      this.currentShowRewardInfo = data.result;
      this.tipDialogTitle = data.title;
      this.tipDialogMessage = data.message;
      this.showTipDialog = true;
    },
    async onLotteryClick(index, lotteryInfo) {
      if (this.activityEnd) {
        //活动已结束
        this.showTravelDialog({
          message: this.$t("couple_activity_end_tip"),
        });
        return;
      }
      if (_.isNil(this.intimacyId)) {
        //没有CP
        this.$emit("on-no-cp-click");
        return;
      }
      if (this.isCollectFinish) {
        //已经收集完成了
        this.showTravelDialog({
          message: this.$t("couple_collect_finish_tip"),
        });
        return;
      }
      const { data, code } = await api.cpTravelLottery({
        intimacyId: this.intimacyId,
        index: index + 1,
        lang: this.$i18n.locale,
      });
      if (code === RES_COMMON_CODE.SUCCESS) {
        if (_.isNil(data)) {
          //戳第一次
          this.showTravelDialog({
            message: this.$t("couple_draw_once_tip"),
            win: false,
          });
          this.changeLotteryStatus(index, LOTTERY_HEART_STATUS.DRAW_ONCE);
        } else if (data.id === 0) {
          //未抽中
          this.showTravelDialog({
            message: this.$t("couple_draw_lose_tip"),
            win: false,
          });
          this.changeLotteryStatus(index, LOTTERY_HEART_STATUS.UN_DRAW);
        } else {
          //抽中了
          this.showTravelDialog({
            title: this.$t("couple_congratulation"),
            message: this.$t("couple_draw_success_tip", {
              name: data.name,
            }),
            win: true,
            result: {
              ticketImg: data.ticketImg,
              name: data.name,
            },
          });
          this.changeLotteryStatus(index, LOTTERY_HEART_STATUS.OPENED);
        }
        this.showTipDialog = true;
        setTimeout(() => {
          this.refreshLotteryInfo();
        }, 2000);
      } else if (code === RES_COMMON_CODE.TIMES_LIMIT) {
        //抽奖券不足
        const { remainingExp, addedExp } = data;
        this.showTravelDialog({
          message:
            this.drawOnceNum === this.lotteryInfos.length
              ? this.$t("couple_travel_draw_over")
              : this.$t("couple_draw_need_intimacy_tip", {
                  current: addedExp,
                  need: remainingExp,
                }),
        });
      } else if (code === RES_COMMON_CODE.LIMIT_CLICK_OTHER) {
        //没有戳同一个气球
        this.showTravelDialog({
          message: this.$t("couple_travel_click_diff_tip"),
        });
      } else if (code == RES_COMMON_CODE.RESCODE_OPERATION_LIMIT) {
        //已经戳过这个气球了
        this.showTravelDialog({
          message:
            this.drawOnceNum === this.lotteryInfos.length
              ? this.$t("couple_travel_draw_over")
              : this.$t("couple_travel_already_click"),
        });
        this.changeLotteryStatus(index, LOTTERY_HEART_STATUS.DRAW_ONCE);
      } else {
        Toast(this.$t("common_failed_with_errorcode", { code }));
      }
    },
    changeLotteryStatus(index, status) {
      var lotteryInfo = this.lotteryInfos[index];
      lotteryInfo.status = status;
      this.$set(this.lotteryInfos, index, lotteryInfo);
    },
    onTipDialogCloseClick() {
      this.showTipDialog = false;
      this.showNextRewardDialog();
    },
  },
  destroyed() {
    this.cancelPollRewardInfo();
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/text.scss" as text;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include v-layout(travel-lottery) {
  width: 690px;
  height: 1222px;
  background: #f7f7f7;
  box-shadow: 0px 0px 2px 0px #c5d1ff,
    inset 0px 1px 3px 2px rgba(255, 255, 255, 0.5);
  border-radius: 16px;
  border: 1px solid #ffffff;
  @include z-layout(activity-gift) {
    width: 560px;
    height: 345px;
    @include image-view(gift-top-bg, 560px, 260px) {
      position: absolute;
      top: 0;
      left: 0;
    }
    @include text-view(
      activity-gift,
      $font-size: 32px,
      $line-height: 45px,
      $font-color: #812445,
      $font-weight: $--font-weight-bold,
      $text-align: center
    ) {
      width: 560px;
      position: absolute;
      top: 32px;
      left: 0px;
    }
    @include image-view(gift-icon, 260px, 260px) {
      position: absolute;
      top: 55px;
      left: 155px;
    }
    @include image-view(gift-stage, 126px, 88px) {
      position: absolute;
      top: 251px;
      left: 217px;
    }
  }
  @include h-layout(lottery-hearts) {
    width: 640px;
    height: 640px;
    background: linear-gradient(3deg, #fdd4e4 0%, #ffe1f2 100%);
    border-radius: 20px;
    border: 3px solid #ffffff;
    padding: 30px 10px;
    flex-wrap: wrap;
    margin-top: 40px;
    @include z-layout(heart-item) {
      width: 180px;
      height: 180px;
      background-image: url("../assets/couple_travel_heart_bg.png");
      background-size: 100% 100%;
      @include compat.margin-start(20px);
      @include image-view(heart, 180px, 180px) {
        position: absolute;
        top: 0;
        left: 0;
        background-image: url("../assets/couple_travel_heart.png");
        background-size: 100% 100%;
        &:active {
          background-image: url("../assets/couple_travel_heart_clicking.png");
        }
      }
      @include image-view(clicked-heart, 180px, 180px) {
        position: absolute;
        top: 0;
        left: 0;
        background-image: url("../assets/couple_travel_heart_clicked.png");
        background-size: 100% 100%;
        &:active {
          width: 180px;
          height: 180px;
          background-image: url("../assets/couple_travel_heart_clicked_clicking.png");
        }
      }
      @include image-view(broken-heart, 180px, 180px) {
        position: absolute;
        top: 0;
        left: 0;
        background-image: url("../assets/couple_travel_heart_broken.png");
        background-size: 100% 100%;
      }
    }
  }
  @include v-layout(bottom) {
    width: 640px;
    height: 211px;
    background: linear-gradient(3deg, #ffdfeb 0%, #ffe9f666 100%);
    box-shadow: inset 0px -4px 18px 12px rgba(255, 148, 175, 0.3);
    margin-top: -40px;
    border-radius: 20px;

    @include text-view(
      lottery-left-count,
      $font-size: 36px,
      $line-height: 50px,
      $font-color: #812445,
      $font-weight: $--font-weight-bold,
      $text-align: center
    ) {
      width: 642px;
      margin-top: 72px;
    }
    @include text-view(
      get-lottery-count-opportunity,
      $font-size: 26px,
      $line-height: 34px,
      $font-color: #bd5a6b,
      $font-weight: $--font-weight-regular,
      $text-align: center
    ) {
      width: 612px;
      margin-top: 12px;
    }
  }
}
@include z-layout(reward-ticket) {
  width: 484px;
  height: 182px;
  @include image-view(reward-ticket, 484px, 182px) {
    position: absolute;
    top: 0;
    left: 0;
  }
  @include text-view(
    romatic-travel,
    $font-size: 24px,
    $line-height: 33px,
    $font-color: #812445,
    $font-weight: $--font-weight-bold,
    $text-align: center
  ) {
    position: absolute;
    top: 48px;
    left: 153px;
  }
  @include text-view(
    ticket-destination,
    $font-size: 30px,
    $line-height: 42px,
    $font-color: #812445,
    $font-weight: $--font-weight-bold,
    $text-align: start
  ) {
    position: absolute;
    top: 94px;
    left: 153px;
    width: 280px;
  }
}
</style>
