<template>

    <div class="recharge-v-layout">
      <div class="accumulative-recharge-h-layout">
        <van-image
          class="currency-image-view"
          :src="require('./assets/trade_recharge_accumulative_recharge_ic.png')"
        />
        <div class="accumulative-recharge-v-layout">
          <span class="accumulative-recharge-text-view">
            {{
              $t("trade_recharge_accumulative_recharge", {
                amount: lastMonthAmount,
              })
            }}
          </span>
          <span class="start-price-text-view">
            {{ $t("trade_recharge_start_price", { count: coinsPrice }) }}
          </span>
        </div>
      </div>
      <!-- <recharge-item-view
        class="margin-top-16px"
        :rechargeWayText="$t('trade_recharge_salary_advance_recharge')"
        @on-recharge-click="onSalaryAdvanceRechargeClick"
        v-if="showSalaryAdvanceRecharge"
      /> -->
      <recharge-item-view
        class="margin-top-16px"
        :rechargeWayText="$t('trade_recharge_payonner_recharge')"
        @on-recharge-click="onPayonnerRechargeClick"
      />
      <!-- <recharge-item-view
        class="margin-top-16px"
        :rechargeWayText="$t('trade_recharge_paypal_recharge')"
        @on-recharge-click="onPaypalRechargeClick"
      /> -->
      <!-- <recharge-item-view
        :class="['margin-top-16px', 'margin-bottom-32px']"
        :rechargeWayText="
          $t('trade_recharge_third_party_recharge_compensation')
        "
        @on-recharge-click="onThirdPartyRechargeCompensationClick"
      /> -->
      <div v-if="showProgressReward" class="h-divide-line" />
      <award-title-view
        v-if="showProgressReward"
        :title="$t('trade_recharge_progress_award')"
      >
        <div class="progress-award-v-layout">
          <div class="progress-h-layout">
            <month-recharge-view
              :rechargeMonthText="
                $t('trade_recharge_in_month', { month: lastMonthText })
              "
              :amount="lastMonthAmount"
            />
            <div class="progress-z-layout">
              <van-image
                class="progress-image-view"
                :src="require('./assets/trade_recharge_progress_ic.png')"
              />
              <span class="progress-text-view">{{ progressRate }}</span>
            </div>
            <month-recharge-view
              :rechargeMonthText="
                $t('trade_recharge_in_month', { month: currentMonthText })
              "
              :amount="rechargeProgressAmount"
            />
          </div>
          <div class="reward-h-layout">
            <div class="reward-grid-layout">
              <award-item-view
                v-for="(award, index) in rechargeProgressAdards"
                :key="index"
                :award="award"
              />
            </div>
          </div>
        </div>
      </award-title-view>
      <!-- <div class="h-divide-line" /> -->
      <!-- <award-title-view
        :title="$t('trade_recharge_month_accumulated_recharge_awards')"
      >
        <div class="accumulated-recharge-award-v-layout">
          <month-accumulated-recharge-award-progress-view
            :currentLevel="level"
            :levelProgress="rechargeLevelProgress"
          />
          <level-award-item-view
            v-for="(levelInfo, index) in rechargeLevelInfos"
            :class="[index > 0 ? 'margin-top-24px' : '']"
            :key="index"
            :showOverAwardTip="index == rechargeLevelInfos.length - 1"
            :currentLevel="level"
            :levelInfo="levelInfo"
          />
        </div>
      </award-title-view> -->
      <van-popup v-model="showSalaryAdvanceRechargeDialog" round>
        <common-dialog
          :titleText="
            $t('trade_recharge_salary_advance_available_amount', {
              amount: availableAdvanceAmount,
            })
          "
          :primaryButtonText="$t('common_confirm')"
          :secondaryButtonText="$t('common_cancel')"
          @on-primary-button-click="onSalaryAdvanceRechargeDialogConfirmClick"
          @on-secondary-button-click="onSalaryAdvanceRechargeDialogCancelClick"
        >
          <div class="salary-advance-v-layout">
            <span class="amount-input-tip-text-view">
              {{ $t("trade_recharge_salary_advance_amount_input_tip") }}
            </span>
            <div class="advance-amount-h-layout">
              <input
                v-model="advanceAmount"
                class="amount-edit-view"
                :placeholder="$t('common_fill_out')"
              />
              <span class="current-name-text-view">$</span>
            </div>
            <span class="advance-limit-text-view">
              {{
                $t("trade_recharge_salary_advance_most_amount", {
                  amount: maxAdvanceAmount,
                })
              }}
            </span>
          </div>
        </common-dialog>
      </van-popup>
      <van-popup v-model="showSalaryAdvanceRechargeSuccessDialog" round>
        <common-dialog
          :messageText="$t('trade_recharge_salary_advance_success_tip')"
          :primaryButtonText="$t('common_confirm')"
          @on-primary-button-click="
            onSalaryAdvanceRechargeSuccessDialogConfirmClick
          "
        >
          <template v-slot:top-img>
            <van-image
              class="recharge-salary-advance-success-image-view"
              :src="
                require('./assets/trade_recharge_salary_advance_success_ic.png')
              "
            />
          </template>
        </common-dialog>
      </van-popup>
      <van-popup v-model="showRechargeCompensationDialog" round>
        <common-dialog
          :messageText="$t('trade_recharge_recharge_compensation_tip')"
          :messageTextStyle="{ textAlign: 'start' }"
          :primaryButtonText="$t('common_recharge')"
          :secondaryButtonText="$t('common_cancel')"
          @on-primary-button-click="onRechargeCompensationDialogRechargeClick"
          @on-secondary-button-click="onRechargeCompensationDialogCancelClick"
        />
      </van-popup>
      <van-popup v-model="showReachLevelDialog" round>
        <achieve-level-dialog
          :currentLevel="level"
          @on-ok-click="onReachLevelDialogOkClick($event)"
        />
      </van-popup>
    </div>
</template>
<script>
import _, { floor } from "lodash";
import moment from "moment";
import Vue from "vue";
import { Toast, Image as VanImage, Popup } from "vant";
import { RES_COMMON_CODE } from "../../../common/network/constant";
import { goRecharge } from "../../../common/bridge/index";
import RechargeItemView from "@/trade/recharge/components/RechargeItemView";
import AwardTitleView from "@/trade/recharge/components/AwardTitleView";
import MonthRechargeView from "@/trade/recharge/components/MonthRechargeView";
import AwardItemView from "@/trade/recharge/components/AwardItemView";
import MonthAccumulatedRechargeAwardProgressView from "@/trade/recharge/components/MonthAccumulatedRechargeAwardProgressView";
import LevelAwardItemView from "@/trade/recharge/components/LevelAwardItemView";
import { mapActions, mapState } from "vuex";
import { RECHARGE_CHANNEL } from "./constant/index";
import CommonDialog from "@/common/components/CommonDialog";
import api from "../api/index";
import AchieveLevelDialog from "@/trade/recharge/components/AchieveLevelDialog";
import { TRADE_RECHARGE_REACH_LEVEL } from "../storage/key";

Vue.use(Toast);
Vue.use(VanImage);
Vue.use(Popup);

const NAVIGATION_END_ICON_ACTION = {
  HISTORY: 0,
};

export default {
  name: "TradeRechargeBody",
  props: {
    showSalaryAdvanceRecharge: {
      type: Boolean,
      default: true,
    },
  },
  components: {
    "recharge-item-view": RechargeItemView,
    "award-title-view": AwardTitleView,
    "month-recharge-view": MonthRechargeView,
    "award-item-view": AwardItemView,
    // "month-accumulated-recharge-award-progress-view":
    //   MonthAccumulatedRechargeAwardProgressView,
    // "level-award-item-view": LevelAwardItemView,
    "common-dialog": CommonDialog,
    "achieve-level-dialog": AchieveLevelDialog,
  },
  created() {
    this.getRechargeActivityInfo();
  },
  data() {
    return {
      showSalaryAdvanceRechargeDialog: false,
      showSalaryAdvanceRechargeSuccessDialog: false,
      showRechargeCompensationDialog: false,
      advanceAmount: "",
      showReachLevelDialog: false,
    };
  },
  methods: {
    ...mapActions("trade", ["getRechargeActivityInfo"]),
    onSalaryAdvanceRechargeClick() {
      this.showSalaryAdvanceRechargeDialog = true;
    },
    async onSalaryAdvanceRechargeDialogConfirmClick() {
      if (_.isEmpty(this.advanceAmount)) {
        return;
      }

      const { code } = await api.recharge({
        channel: RECHARGE_CHANNEL.SALARY_ADVANCE,
        amount: this.advanceAmount,
      });

      if (code === RES_COMMON_CODE.BALANCE_NOT_ENOUGH) {
        Toast(this.$t("common_insufficient_balance"));
        return;
      }

      if (code !== RES_COMMON_CODE.SUCCESS) {
        Toast(this.$t("common_failed_with_errorcode", { code }));
        return;
      }

      this.showSalaryAdvanceRechargeDialog = false;
      this.getRechargeActivityInfo();
      this.showSalaryAdvanceRechargeSuccessDialog = true;
    },
    onSalaryAdvanceRechargeDialogCancelClick() {
      this.showSalaryAdvanceRechargeDialog = false;
    },
    onSalaryAdvanceRechargeSuccessDialogConfirmClick() {
      this.showSalaryAdvanceRechargeSuccessDialog = false;
    },
    onPayonnerRechargeClick() {
      this.$router.push(
        `/trade/recharge/channel_recharge/${RECHARGE_CHANNEL.PAYONNER}`
      );
    },
    onPaypalRechargeClick() {
      this.$router.push(
        `/trade/recharge/channel_recharge/${RECHARGE_CHANNEL.PAYPAL}`
      );
    },
    onThirdPartyRechargeCompensationClick() {
      this.showRechargeCompensationDialog = true;
    },
    async onRechargeCompensationDialogRechargeClick() {
      this.showRechargeCompensationDialog = false;
      goRecharge();
    },
    onRechargeCompensationDialogCancelClick() {
      this.showRechargeCompensationDialog = false;
    },
    onReachLevelDialogOkClick(level) {
      this.showReachLevelDialog = false;
      this.$localStorage.set(this.reacheLevelKey(level), true);
    },
    reacheLevelKey(level) {
      const currentMoment = moment();
      const year = currentMoment.year();
      const month = currentMoment.month();
      return `${TRADE_RECHARGE_REACH_LEVEL}_${year}_${month}_${level}`;
    },
  },
  computed: {
    ...mapState("trade", {
      level: (state) => state.userRechargeInfo.level,
      lastMonthAmount: (state) => state.userRechargeInfo.lastMonthAmount,
      rechargeProgressAmount: (state) =>
        state.userRechargeInfo.rechargeProgressAmount,
      coinsPrice: (state) => state.userRechargeInfo.coinsPrice,
      showProgressReward: (state) => state.userRechargeInfo.showProgressReward,
      rechargeProgressAdards: (state) => state.rechargeProgressAdards,
      rechargeLevelInfos: (state) => state.rechargeLevelInfos,
      availableAdvanceAmount: (state) => state.userRechargeInfo.advanceAmount,
      maxAdvanceAmount: (state) =>
        state.userRechargeInfo.maxSingleAdvanceAmount,
      rechargeLevelProgress: (state) => state.rechargeLevelProgress,
    }),
    currentMonthText() {
      return moment().format("MMM");
    },
    lastMonthText() {
      return moment().subtract(1, "months").format("MMM");
    },
    progressRate() {
      const lastMonthAmount = this.lastMonthAmount;
      if (lastMonthAmount === 0) {
        return "0%";
      }

      const rechargeProgressAmount = this.rechargeProgressAmount;
      if (rechargeProgressAmount > lastMonthAmount) {
        return `${floor(
          ((rechargeProgressAmount - lastMonthAmount) * 100) / lastMonthAmount
        )}%`;
      }
      return "0%";
    },
  },
  watch: {
    "$store.state.trade.rechargeActivityInfoRes": function (res, oldRes) {
      if (_.isNil(res)) {
        return;
      }

      const { code } = res;
      if (code !== RES_COMMON_CODE.SUCCESS) {
        Toast(this.$t("common_failed_with_errorcode", { code }));
        return;
      }
    },
    "$store.state.trade.userRechargeInfo.level": function (level, oldRes) {
      this.showReachLevelDialog =
        level > 0 &&
        !this.$localStorage.get(this.reacheLevelKey(level), false, Boolean);
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(recharge) {
  width: 100%;

  @include h-layout(accumulative-recharge) {
    width: 100%;
    height: 200px;
    background: linear-gradient(90deg, #ffe3b9 0%, #fff5e0 100%);
    padding: 0px 40px;

    @include image-view(currency, 90px, 164px) {
      align-self: flex-end;
    }

    @include v-layout(accumulative-recharge, flex-start) {
      @include compat.margin-start(40px);

      @include text-view(
        accumulative-recharge,
        $font-size: 26px,
        $line-height: 40px,
        $font-color: #a88c6b,
        $font-weight: $--font-weight-regular
      );

      @include text-view(
        start-price,
        $font-size: 32px,
        $line-height: 40px,
        $font-color: #795228,
        $font-weight: $--font-weight-medium
      ) {
        margin-top: 8px;
      }
    }
  }

  .margin-top-16px {
    margin-top: 16px;
  }

  .margin-bottom-32px {
    margin-bottom: 32px;
  }

  @include divide-line(h, 100%, 16px, $--color-F5F7FA);

  .margin-top-24px {
    margin-top: 24px;
  }

  @include v-layout(progress-award) {
    width: 670px;
    background: #fefcf5;
    border-radius: 16px;
    border: 2px solid #efe7d2;

    @include h-layout(progress) {
      height: 200px;
      justify-content: space-between;

      @include z-layout(progress) {
        @include image-view(progress, 120px, 120px) {
          @include dir {
            transform: rotateY(180deg);
          }
        }

        @include text-view(
          progress,
          $font-size: 24px,
          $line-height: 33px,
          $font-color: #fe1b58,
          $font-weight: $--font-weight-medium
        ) {
          position: absolute;
          top: 68px;
          left: 50%;
          transform: translateX(-50%);
        }
      }
    }

    @include h-layout(reward) {
      width: 100%;
      background: #fff5e0;
      border-radius: 0px 0px 16px 16px;
      border: 2px solid #efe7d2;
      padding: 32px 23px 20px 23px;

      @include grid-layout(reward, 144px 144px 144px 144px, 202px, 16px, 8px);
    }
  }

  @include v-layout(accumulated-recharge-award) {
    width: 100%;
  }

  @include v-layout(salary-advance) {
    width: 100%;
    @include text-view(
      amount-input-tip,
      $font-size: 32px,
      $line-height: 48px,
      $font-color: $--color-222222,
      $font-weight: $--font-weight-regular
    );
    @include h-layout(advance-amount) {
      width: 100%;
      height: 98px;
      background: $--color-F5F7FA;
      border-radius: 24px;
      justify-content: space-between;
      padding: 0px 24px;
      margin-top: 16px;

      @include edit-view(
        amount,
        $width: 436px,
        $height: 98px,
        $font-size: 28px,
        $font-color: $--color-AAAAAA,
        $font-weight: $--font-weight-regular,
        $background-color: $--color-F5F7FA
      );
      @include text-view(
        current-name,
        $font-size: 36px,
        $line-height: 48px,
        $font-color: $--color-222222,
        $font-weight: $--font-weight-bold
      );
      @include text-view(
        advance-limit,
        $font-size: 26px,
        $line-height: 48px,
        $font-color: $--color-777777,
        $font-weight: $--font-weight-regular
      ) {
        margin-top: 8px;
      }
    }
  }
  @include image-view(recharge-salary-advance-success, 180px, 160px);
}
</style>