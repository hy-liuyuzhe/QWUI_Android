<template>
  <div class="we-page">
    <div class="page-backgroud" />
    <navigation-bar
      class="navigation-bar"
      :type="navigationStyle"
      :title="navigationTitle"
      :endIcons="navigationEndIcons"
      @on-back-click="onNavBackClick($event)"
      @on-end-icon-click="onNavEndIconClick($event)"
    />
    <div :class="['content-v-layout', isIOS ? 'is-ios' : '']">
      <van-image
        class="machine-image-view"
        :src="require('./assets/super_gift_lottery_machine.png')"
      />
      <div class="lottery-v-layout">
        <van-notice-bar
          class="notice-bar"
          color="#FFC529"
          background="rgba(255, 255, 255, 0)"
          :scrollable="false"
        >
          <van-swipe
            vertical
            class="notice-swipe"
            :autoplay="3000"
            :show-indicators="false"
          >
            <van-swipe-item
              v-for="(result, index) in latest20LotteryResults"
              :key="index"
            >
              <div class="latest-lottery-results-h-layout">
                <span class="user-name-text-view">
                  {{ result.user.name }}
                </span>
                <span class="draw-text-view">
                  {{ $t("super_gift_lottery_draw") }}
                </span>
                <span class="lottery-result-text-view">
                  {{ result.winGoods.fullName }}
                </span>
              </div>
            </van-swipe-item>
          </van-swipe>
        </van-notice-bar>

        <div class="prizes-grid-layout">
          <div
            v-for="(item, index) in positions"
            :key="index"
            class="prize-v-layout"
          >
            <van-image class="prize-icon-image-view" :src="item.goods.icon" />
            <span class="prize-name-text-view">{{ item.goods.name }}</span>
          </div>
        </div>
        <span class="ticket-remain-num-text-view">
          {{
            $t("super_gift_lottery_tickets_amount", { tickets: lotteryTickets })
          }}
        </span>
        <div class="lottery-btns-h-layout">
          <div class="lottery-once-btn-v-layout" @click="lotteryOnce">
            <span class="times-text-view">
              {{ $t("super_gift_lottery_once") }}
            </span>
            <div class="tickets-or-coins-num-h-layout">
              <span :class="['tickets-text-view', 'is-once']">1</span>
              <span :class="['or-text-view', 'is-once']">or</span>
              <span :class="['gold-coins-text-view', 'is-once']">1000</span>
            </div>
          </div>
          <div class="lottery-ten-times-btn-v-layout" @click="lottery10Times">
            <span class="times-text-view">
              {{ $t("super_gift_lottery_ten_times") }}
            </span>
            <div class="tickets-or-coins-num-h-layout">
              <span class="tickets-text-view">9</span>
              <span class="or-text-view">or</span>
              <span class="gold-coins-text-view">9000</span>
            </div>
          </div>
        </div>
        <div class="tabs-h-layout">
          <span
            :class="['tab-text-view', showEnergyValueTab ? 'is-selected' : '']"
            @click="onEnergyValueTabClick"
          >
            {{ $t("super_gift_lottery_energy_value") }}
          </span>
          <span
            :class="['tab-text-view', showEnergyValueTab ? '' : 'is-selected']"
            @click="onDebrisTabClick"
          >
            {{ $t("super_gift_lottery_debris") }}
          </span>
        </div>
        <div v-show="showEnergyValueTab" class="energy-value-page-v-layout">
          <div class="energy-value-h-layout">
            <van-image
              class="energy-value-icon-image-view"
              :src="
                require('./assets/super_gift_lottery_energy_value_big_ic.png')
              "
            />
            <span class="energy-value-text-view">
              {{ $t("super_gift_lottery_energy_value_of_month") }}
            </span>
            <span class="energy-value-count-text-view">
              {{ currentEnergyValue }}
            </span>
          </div>
          <div class="energy-value-progress-v-layout">
            <energy-value-progress-view
              :activityId="activityId"
              :currentEnergy="currentEnergyValue"
              :energyProgress="energyProgress"
              :receivedEnergyValues="receivedEnergyValues"
              @on-receive-box-click="onReceiveBoxClick($event)"
            />
            <span class="next-energy-value-need-text-view">
              {{
                $t("super_gift_lottery_next_energy_value_need", {
                  value: nextEnergyValueNeed,
                })
              }}
            </span>
          </div>
          <div class="go-rule-h-layout" @click="onEnergyValueClick">
            <span class="rule-text-view">{{ $t("common_rule") }}</span>
            <van-image
              class="arrow-icon-image-view"
              :src="
                require('./assets/super_gift_lottery_energy_value_rule_arrow_ic.png')
              "
            />
          </div>
        </div>
        <div v-show="!showEnergyValueTab" class="debris-page-v-layout">
          <div class="exchange-debris-h-layout">
            <van-image
              class="debris-icon-image-view"
              :src="require('./assets/super_gift_lottery_debris_ic.png')"
            />
            <span class="remaining-debris-text-view">
              {{ $t("super_gift_lottery_remaining_debris") }}
            </span>
            <span class="debris-count-text-view">{{ remainingDebris }}</span>
          </div>

          <div class="exchange-list-v-layout">
            <debris-exchange-item-view
              class="debris-exchange-item-view"
              v-for="(item, index) in exchangeItems"
              :key="index"
              :exchangeItem="item"
              @on-exchange-click="onExchangeClick($event)"
            />
          </div>
        </div>
      </div>
    </div>
    <van-popup v-model="showLotteryResultDialog">
      <super-gift-lottery-result-dialog
        v-bind:prizeList="drawPrizeList"
        @on-close-btn-click="onLotteryResultDialogCloseClick($event)"
      />
    </van-popup>
    <van-popup v-model="showExchangeConfirmDialog">
      <exchange-confirm-dialog
        @on-cancel-btn-click="onExchangeConfirmDialogCancelClick"
        @on-confirm-btn-click="onExchangeConfirmDialogConfirmClick"
      />
    </van-popup>
    <van-popup
      v-model="showHistoryRecordsPopup"
      position="bottom"
      @open="onHistoryRecordsPopupOpen"
      @closed="onHistoryRecordsPopupClosed"
    >
      <history-records-popup v-if="showHistoryRecordsView" />
    </van-popup>
    <van-popup v-model="showEnergyValueRulePopup" position="bottom">
      <energy-value-rule-popup />
    </van-popup>
    <van-popup
      v-model="showEnergyValueBoxResultDialog"
      get-container="body"
      round
    >
      <energy-value-box-result-dialog
        v-bind:prize="energyValueBoxPrize"
        @on-accept-btn-click="onEnergyValueBoxAcceptClick($event)"
      />
    </van-popup>
  </div>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import {
  hideNavigationBar,
  setNavigationBarDarkMode,
  closeWindow,
} from "../../../common/bridge/index";
import {
  Image as VanImage,
  Toast,
  Popup,
  NoticeBar,
  Swipe,
  SwipeItem,
} from "vant";
import DebrisExchangeItemView from "@/lottery/super-gift-lottery/components/DebrisExchangeItemView";
import api from "../api/index";
import { RES_COMMON_CODE } from "../../../common/network/constant";
import {
  ACTIVITY_TYPE,
  LOTTERY_TYPE,
  LOTTERY_PRIZE_TYPE,
} from "../constant/index";
import SuperGiftLotteryResultDialog from "@/lottery/super-gift-lottery/components/SuperGiftLotteryResultDialog";
import ExchangeConfirmDialog from "@/lottery/super-gift-lottery/components/ExchangeConfirmDialog";
import HistoryRecordsPopup from "@/lottery/super-gift-lottery/components/HistoryRecordsPopup";
import EnergyValueRulePopup from "@/lottery/super-gift-lottery/components/EnergyValueRulePopup";
import EnergyValueProgressView from "@/lottery/super-gift-lottery/components/EnergyValueProgressView";
import EnergyValueBoxResultDialog from "@/lottery/super-gift-lottery/components/EnergyValueBoxResultDialog";
import NavigationBar from "@/common/components/NavigationBar";
import { NAVIGATION_BAR_STYLE } from "@/common/constant/index";
import { isIOS } from "../../../common/util/index";

Vue.use(VanImage);
Vue.use(Toast);
Vue.use(Popup);
Vue.use(NoticeBar);
Vue.use(Swipe);
Vue.use(SwipeItem);

const NAVIGATION_END_ICON_ACTION = {
  HISTORY_RECORDS: 0,
};

export default {
  name: "SuperGiftLottery",
  components: {
    "navigation-bar": NavigationBar,
    "debris-exchange-item-view": DebrisExchangeItemView,
    "super-gift-lottery-result-dialog": SuperGiftLotteryResultDialog,
    "exchange-confirm-dialog": ExchangeConfirmDialog,
    "history-records-popup": HistoryRecordsPopup,
    "energy-value-rule-popup": EnergyValueRulePopup,
    "energy-value-progress-view": EnergyValueProgressView,
    "energy-value-box-result-dialog": EnergyValueBoxResultDialog,
  },
  created() {
    hideNavigationBar();
    setNavigationBarDarkMode();
    this.getLotteryActivity();
    this.getRemainingDebris();
    this.getLotteryTickets();
    this.getExchangeItems();
    this.getLatest20LotteryResults();
  },
  data() {
    return {
      navigationStyle: NAVIGATION_BAR_STYLE.LIGHT,
      navigationTitle: "",
      navigationEndIcons: [
        {
          action: NAVIGATION_END_ICON_ACTION.HISTORY_RECORDS,
          icon: require("../../common/assets/common_history_white_48_ic.png"),
        },
      ],
      activityId: 0,
      positions: [],
      drawPrizeList: [],
      latest20LotteryResults: [],
      remainingDebris: 0,
      lotteryTickets: 0,
      showLotteryResultDialog: false,
      exchangeItems: [],
      toExchangeItem: null,
      showExchangeConfirmDialog: false,
      showHistoryRecordsPopup: false,
      showHistoryRecordsView: false, //关闭历史记录时，清空数据
      showEnergyValueRulePopup: false,
      showEnergyValueTab: true,
      currentEnergyValue: 0, //当前能量值
      energyProgress: [], //能量值礼盒进度
      receivedEnergyValues: [], //已领取进度
      showEnergyValueBoxResultDialog: false,
      energyValueBoxPrize: null,
    };
  },
  methods: {
    isIOS() {
      return isIOS;
    },
    onNavBackClick() {
      closeWindow();
    },
    onNavEndIconClick(action) {
      if (action === NAVIGATION_END_ICON_ACTION.HISTORY_RECORDS) {
        this.showHistoryRecordsPopup = true;
      }
    },
    onHistoryRecordsPopupOpen() {
      this.showHistoryRecordsView = true;
    },
    onHistoryRecordsPopupClosed() {
      this.showHistoryRecordsView = false;
    },
    async getLotteryActivity() {
      const res = await api.getLotteryActivity(
        ACTIVITY_TYPE.SUPER_GIFT_LOTTERY
      );
      const { code, data } = res;
      if (code === RES_COMMON_CODE.SUCCESS) {
        const {
          activityId,
          positions,
          userEnergy,
          energyBox,
          energyProgressTakeReward,
        } = data;
        this.activityId = activityId;
        this.positions = positions;
        this.currentEnergyValue = userEnergy;
        this.energyProgress = energyBox;
        this.receivedEnergyValues = energyProgressTakeReward;
        return;
      }

      if (code === RES_COMMON_CODE.ACTIVITY_EXPIRED) {
        Toast(this.$t("common_activity_end"));
        return;
      }

      Toast(this.$t("common_failed_with_errorcode", { code }));
    },
    async getRemainingDebris() {
      const res = await api.getDebris();
      const { code, data } = res;
      if (code === RES_COMMON_CODE.SUCCESS) {
        const currency = _.head(data.currencyInfo);
        if (currency) {
          this.remainingDebris = currency.currencyValue;
        }
        return;
      }

      Toast(this.$t("common_failed_with_errorcode", { code }));
    },
    async getLotteryTickets() {
      const res = await api.getLotteryTickets();
      const { code, data } = res;
      if (code === RES_COMMON_CODE.SUCCESS) {
        const currency = _.head(data.currencyInfo);
        if (currency) {
          this.lotteryTickets = currency.currencyValue;
        }
        return;
      }

      Toast(this.$t("common_failed_with_errorcode", { code }));
    },
    async getExchangeItems() {
      const res = await api.getDebrisExchangeItems(
        ACTIVITY_TYPE.SUPER_GIFT_LOTTERY
      );
      const { code, data } = res;
      if (code === RES_COMMON_CODE.SUCCESS) {
        this.exchangeItems = data;
        return;
      }

      Toast(this.$t("common_failed_with_errorcode", { code }));
    },
    async getLatest20LotteryResults() {
      const res = await api.getLatest20LotteryResults(
        ACTIVITY_TYPE.SUPER_GIFT_LOTTERY
      );
      const { code, data } = res;
      if (code === RES_COMMON_CODE.SUCCESS) {
        this.latest20LotteryResults = data;
        return;
      }

      Toast(this.$t("common_failed_with_errorcode", { code }));
    },
    async lotteryOnce() {
      this.lottery(LOTTERY_TYPE.ONCE);
    },
    async lottery10Times() {
      this.lottery(LOTTERY_TYPE.TEN_TIMES);
    },
    async lottery(lotteryType) {
      const { code, data } = await api.lottery(this.activityId, lotteryType);
      if (code === RES_COMMON_CODE.SUCCESS) {
        this.drawPrizeList = data.goodsList;
        this.showLotteryResultDialog = true;
        if (
          _.find(
            data.goodsList,
            (goods) => goods.type === LOTTERY_PRIZE_TYPE.DEBRIS
          )
        ) {
          this.getRemainingDebris();
        }
        this.getLotteryTickets();
        return;
      }

      if (
        code === RES_COMMON_CODE.BALANCE_NOT_ENOUGH ||
        code === RES_COMMON_CODE.SPEND_CURRENCY_FAILED
      ) {
        Toast(this.$t("common_coins_not_enough"));
        return;
      }

      Toast(this.$t("common_failed_with_errorcode", { code }));
    },
    onExchangeClick(exchangeItem) {
      this.toExchangeItem = exchangeItem;
      this.showExchangeConfirmDialog = true;
    },
    onLotteryResultDialogCloseClick() {
      this.showLotteryResultDialog = false;
    },
    onExchangeConfirmDialogCancelClick() {
      this.showExchangeConfirmDialog = false;
    },
    async onExchangeConfirmDialogConfirmClick() {
      this.showExchangeConfirmDialog = false;
      const { code } = await api.debrisExchange(this.toExchangeItem.id);
      if (code === RES_COMMON_CODE.SUCCESS) {
        Toast(this.$t("common_success"));
        this.getRemainingDebris();
        return;
      }

      if (
        code === RES_COMMON_CODE.BALANCE_NOT_ENOUGH ||
        code === RES_COMMON_CODE.SPEND_CURRENCY_FAILED
      ) {
        Toast(this.$t("super_gift_lottery_debris_not_enough"));
        return;
      }

      Toast(this.$t("common_failed_with_errorcode", { code }));
    },
    onEnergyValueTabClick() {
      this.showEnergyValueTab = true;
    },
    onDebrisTabClick() {
      this.showEnergyValueTab = false;
    },
    onEnergyValueClick() {
      this.showEnergyValueRulePopup = true;
    },
    async onReceiveBoxClick(energy) {
      const { code } = await api.openLotteryEnergyBox(
        this.activityId,
        energy.chargeCoinNum
      );
      if (code === RES_COMMON_CODE.SUCCESS) {
        this.receivedEnergyValues.push(energy.chargeCoinNum);
        this.getLotteryTickets();
        this.energyValueBoxPrize = energy.rewardPackage[0];
        this.showEnergyValueBoxResultDialog = true;
      } else {
        Toast(this.$t("common_failed_with_errorcode", { code: code }));
      }
    },
    onEnergyValueBoxAcceptClick() {
      this.showEnergyValueBoxResultDialog = false;
    },
  },
  computed: {
    nextEnergyValueNeed() {
      const energy = _.find(
        this.energyProgress,
        (energy) => energy.chargeCoinNum > this.currentEnergyValue
      );
      if (energy) {
        return energy.chargeCoinNum - this.currentEnergyValue;
      }

      return 0;
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include we(page) {
  width: 100%;
  box-sizing: border-box;
  
  .page-backgroud {
    width: 100%;
    height: 100%;
    background-image: url("./assets/super_gift_lottery_bg.png");
    background-repeat: no-repeat;
    background-size: cover;
    position: fixed;
  }

  .navigation-bar {
    @include compat.sticky;
    top: 0;
    z-index: 1;
  }

  @include v-layout(content) {
    position: absolute;
    width: 750px;
    top: 0px;
    @include when(ios) {
      top: 30px;
    }

    @include image-view(machine, 750px, 1590px);
    @include v-layout(lottery) {
      position: absolute;
      top: 263px;

      .notice-bar {
        width: 600px;
        height: 40px;
        .notice-swipe {
          height: 40px;
          line-height: 40px;
          .van-swipe-item {
            width: 600px;
            color: #ffc529;
            font-size: 24px;
            line-height: 33px;
            text-align: center;
          }
        }
      }

      @include h-layout(latest-lottery-results) {
        width: 100%;
        justify-content: center;

        @include text-view(
          user-name,
          $font-size: 24px,
          $line-height: 33px,
          $font-color: $--color-FFC529,
          $font-weight: $--font-weight-medium,
          $font-family: $--font-family-PingFangSC
        );

        @include text-view(
          draw,
          $font-size: 24px,
          $line-height: 33px,
          $font-color: $--color-DB6E2C,
          $font-weight: $--font-weight-medium,
          $font-family: $--font-family-PingFangSC
        ) {
          @include compat.margin-start(8px);
        }

        @include text-view(
          lottery-result,
          $font-size: 24px,
          $line-height: 33px,
          $font-color: $--color-FFC529,
          $font-weight: $--font-weight-medium,
          $font-family: $--font-family-PingFangSC
        ) {
          @include compat.margin-start(8px);
        }
      }

      @include grid-layout(
        prizes,
        174px 174px 174px,
        160px 160px 160px,
        16px,
        16px
      ) {
        margin-top: 30px;

        @include v-layout(prize) {
          position: relative;
          width: 174px;
          height: 160px;
          justify-content: center;
          background-image: url("./assets/super_gift_lottery_prize_bg.png");
          background-repeat: no-repeat;
          background-size: cover;

          @include image-view(prize-icon, 100px, 100px);

          @include text-view(
            prize-name,
            $font-size: 24px,
            $line-height: 29px,
            $font-color: rgba(255, 255, 255, 0.6),
            $font-weight: $--font-weight-medium
          ) {
            margin-top: 8px;
          }
        }
      }

      @include text-view(
        ticket-remain-num,
        $font-size: 26px,
        $line-height: 37px,
        $font-color: $--color-FFC529,
        $font-weight: $--font-weight-medium,
        $font-family: $--font-family-PingFangSC
      ) {
        margin-top: 23px;
      }

      @include h-layout(lottery-btns) {
        margin-top: 13px;

        @include v-layout(lottery-btn) {
          width: 290px;
          height: 112px;

          @include text-view(
            times,
            $font-size: 40px,
            $line-height: 48px,
            $font-color: $--color-white,
            $font-weight: $--font-weight-medium
          ) {
            margin-top: 10px;
          }

          @include h-layout(tickets-or-coins-num) {
            width: 290px;
            direction: ltr;
            @include text-view(
              tickets,
              $font-size: 24px,
              $line-height: 33px,
              $font-color: $--color-BE58FB,
              $font-weight: $--font-weight-medium,
              $text-align: center
            ) {
              width: 46px;
              @include compat.margin-start(22px);
              @include when(once) {
                color: $--color-D38A16;
              }
            }
            @include text-view(
              or,
              $font-size: 22px,
              $line-height: 30px,
              $font-color: $--color-BE58FB,
              $font-weight: $--font-weight-medium,
              $text-align: center
            ) {
              width: 36px;
              @include compat.margin-start(24px);
              @include when(once) {
                color: $--color-D38A16;
              }
            }
            @include text-view(
              gold-coins,
              $font-size: 24px,
              $line-height: 33px,
              $font-color: $--color-BE58FB,
              $font-weight: $--font-weight-medium,
              $text-align: center
            ) {
              width: 110px;
              @include when(once) {
                color: $--color-D38A16;
              }
            }
          }
        }

        @include v-layout(lottery-once-btn) {
          @extend .lottery-btn-v-layout;
          background-image: url("./assets/super_gift_lottery_once_btn_bg.png");
          background-repeat: no-repeat;
          background-size: 290px 112px;
        }
        .lottery-once-btn-v-layout:active {
          background-image: url("./assets/super_gift_lottery_once_btn_pressed_bg.png");
        }

        @include v-layout(lottery-ten-times-btn) {
          @extend .lottery-btn-v-layout;
          background-image: url("./assets/super_gift_lottery_ten_times_btn_bg.png");
          background-repeat: no-repeat;
          background-size: 290px 112px;
          @include compat.margin-start(22px);
        }
        .lottery-ten-times-btn-v-layout:active {
          background-image: url("./assets/super_gift_lottery_ten_times_btn_pressed_bg.png");
        }
      }

      @include h-layout(tabs) {
        width: 440px;
        height: 40px;
        border-radius: 36px;
        background: #66071e;
        margin-top: 36px;
        @include text-view(
          tab,
          $font-size: 24px,
          $line-height: 40px,
          $font-color: rgba(255, 255, 255, 0.5),
          $font-weight: $--font-weight-bold,
          $text-align: center,
          $line-number: 1
        ) {
          width: 220px;
          border-radius: 36px;
          @include when(selected) {
            color: #ffdee7;
            background: #bd2851;
          }
        }
      }

      @include v-layout(debris-page) {
        width: 100%;
        @include h-layout(exchange-debris) {
          margin-top: 22px;

          @include image-view(debris-icon, 40px, 40px);

          @include text-view(
            remaining-debris,
            $font-size: 26px,
            $line-height: 37px,
            $font-color: $--color-FFC529,
            $font-weight: $--font-weight-medium,
            $line-number: 1
          ) {
            width: 408px;
            @include compat.margin-start(8px);
          }

          @include text-view(
            debris-count,
            $font-size: 26px,
            $line-height: 37px,
            $font-color: $--color-FFC529,
            $font-weight: $--font-weight-medium,
            $text-align: end,
            $line-number: 1
          ) {
            width: 150px;
          }
        }

        @include v-layout(exchange-list) {
          display: block;
          width: 640px;
          height: 407px;
          margin-top: 15px;
          overflow-y: overlay;

          .debris-exchange-item-view {
            margin-bottom: 10px;
          }
        }
      }

      @include v-layout(energy-value-page) {
        width: 100%;

        @include h-layout(energy-value) {
          margin-top: 22px;

          @include image-view(energy-value-icon, 40px, 40px);

          @include text-view(
            energy-value,
            $font-size: 26px,
            $line-height: 37px,
            $font-color: $--color-FFC529,
            $font-weight: $--font-weight-medium,
            $line-number: 1
          ) {
            width: 408px;
            @include compat.margin-start(8px);
          }

          @include text-view(
            energy-value-count,
            $font-size: 26px,
            $line-height: 37px,
            $font-color: $--color-FFC529,
            $font-weight: $--font-weight-medium,
            $text-align: end,
            $line-number: 1
          ) {
            width: 150px;
          }
        }

        @include v-layout(energy-value-progress) {
          width: 640px;
          background: rgba(123, 39, 25, 0.8);
          border-radius: 14px;
          padding: 32px 18px 32px 18px;
          margin-top: 15px;

          @include text-view(
            next-energy-value-need,
            $font-size: 22px,
            $line-height: 26px,
            $font-color: #db6e2c,
            $font-weight: $--font-weight-medium
          ) {
            margin-top: 24px;
            align-items: flex-start;
          }
        }

        @include h-layout(go-rule) {
          width: 240px;
          height: 56px;
          background: rgba(123, 39, 25, 0.8);
          border-radius: 28px;
          margin-top: 10px;
          justify-content: center;

          @include text-view(
            rule,
            $font-size: 28px,
            $line-height: 40px,
            $font-color: #db6e2c,
            $font-weight: $--font-weight-medium
          );

          @include image-view(arrow-icon, 32px, 32px) {
            @include compat.margin-start(8px);
          }
        }
      }
    }
  }
}
</style>