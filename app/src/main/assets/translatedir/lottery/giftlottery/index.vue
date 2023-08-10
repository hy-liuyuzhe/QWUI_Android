<template>
  <div class="root">
    <div class="bg"></div>
    <div class="board">
      <span class="date-text">{{ formatDate }}</span>
      <div class="goods-grid-layout">
        <div v-for="(item, index) in positions" :key="index" class="goods-item">
          <van-image class="icon" :src="item.goods.icon" />
          <span class="desc">{{ item.goods.name }}</span>
        </div>
      </div>
    </div>
    <div :class="[light1 ? 'board-light-1' : 'board-light-2']"></div>
    <div class="btn-layout">
      <div class="raffle-ticket-btn">
        <van-image
          class="raffle-ticket-icon"
          :src="require('./assets/raffle_ticket_ic.png')"
        />
        <div class="ticket-layout">
          <span class="raffle-ticket-num">
            {{ activityId > 0 ? raffleTicketNum : 0 }}
          </span>
          <span class="ticket-text">{{ $t("gift_lottery_ticket") }}</span>
        </div>
      </div>
      <div class="lottery-btn" @click="onLotteryClick()">
        <span class="btn-text">{{ $t("gift_lottery_lottery") }}</span>
      </div>
    </div>
    <div class="rule-placeholder"></div>
    <div class="rule-layout">
      <span class="rule-text">{{ $t("gift_lottery_rule") }}</span>
    </div>
    <van-popup v-model="showResult">
      <lottery-result
        v-bind:goods="winGoods"
        @close-btn-click="onResultCloseClick($event)"
      ></lottery-result>
    </van-popup>
  </div>
</template>

<script>
import Vue from "vue";
import { Toast, Popup, Image as VanImage } from "vant";
import moment from "moment";
import _ from "lodash";
import api from "../api/index";
import { ACTIVITY_TYPE, LOTTERY_TYPE } from "../constant/index";
import { RES_COMMON_CODE } from "../../../common/network/constant";
import LotteryResult from "./components/LotteryResult";

Vue.use(Popup);
Vue.use(Toast);
Vue.use(VanImage);

export default {
  name: "GiftLottery",
  components: {
    "lottery-result": LotteryResult,
  },
  created() {
    this.getLotteryActivity();
    this.getRaffleTicket();
    this.lightChange();
  },
  beforeDestroy() {
    clearTimeout(this.lightTimeout);
  },
  data: function () {
    return {
      activityId: 0,
      startTime: 0,
      endTime: 0,
      positions: [],
      winGoods: {},
      showResult: false,
      raffleTicketNum: 0,
      lightTimeout: null,
      light1: true,
    };
  },
  methods: {
    async getLotteryActivity() {
      const res = await api.getLotteryActivity(ACTIVITY_TYPE.GIFT_LOTTERY);
      const { code, data } = res;
      if (code === RES_COMMON_CODE.SUCCESS) {
        const { activityId, startTime, endTime, positions } = data;
        this.activityId = activityId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.positions = positions;
      } else if (code === RES_COMMON_CODE.ACTIVITY_EXPIRED) {
        Toast(this.$t("common_activity_end"));
      }
    },
    async getRaffleTicket() {
      const res = await api.getRaffleTicket();
      const { code, data } = res;
      if (code === RES_COMMON_CODE.SUCCESS) {
        const currency = _.head(data.currencyInfo);
        if (currency) {
          this.raffleTicketNum = currency.currencyValue;
        }
      }
    },
    async onLotteryClick() {
      if (this.raffleTicketNum === 0) {
        Toast(this.$t("gift_lottery_ticket_not_enough"));
        this.getRaffleTicket();
        return;
      }

      const res = await api.lottery(this.activityId, LOTTERY_TYPE.ONCE);
      const { code, data } = res;
      if (code === RES_COMMON_CODE.SUCCESS) {
        this.winGoods = data.goods;
        this.showResult = true;
        this.getRaffleTicket();
      }
    },
    onResultCloseClick() {
      this.showResult = false;
    },
    lightChange() {
      this.light1 = !this.light1;
      this.lightTimeout = setTimeout(() => {
        this.lightChange();
      }, 300);
    },
  },
  computed: {
    formatDate() {
      return `${moment
        .unix(this.startTime / 1000)
        .format("YYYY.MM.DD")}-${moment
        .unix(this.endTime / 1000)
        .format("MM.DD")}`;
    },
  },
  metaInfo: {
    title: "Lucky Lottery",
    meta: [
      {
        name: "viewport",
        content: "width=device-width, initial-scale=1, maximum-scale=1",
      },
    ],
  },
};
</script>

<style lang="scss" scoped>
@import "../../greedy/css/common.scss";
.root {
  width: 750px;
  .bg {
    width: 100%;
    height: 100%;
    position: fixed;
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover;
    background-image: url("./assets/bg.png");
  }
  .board {
    width: 750px;
    height: 975px;
    align-self: center;
    background-repeat: no-repeat;
    background-position: center;
    background-size: 100% 100%;
    background-image: url("./assets/board_ic.png");
    position: absolute;
    top: 65px;
    display: flex;
    flex-direction: column;
    align-items: center;
    .date-text {
      @extend .single-line-text;
      margin-top: 152px;
      width: 242px;
      height: 40px;
      font-size: 28px;
      font-family: Helvetica;
      color: #ffffff;
      line-height: 34px;
    }
    .goods-grid-layout {
      margin-top: 22px;
      display: grid;
      grid-template-columns: 202px 202px 202px;
      grid-template-rows: 202px 202px 202px;
      grid-row-gap: 12px;
      grid-column-gap: 12px;
      .goods-item {
        position: relative;
        width: 202px;
        height: 202px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        .icon {
          width: 146px;
          height: 146px;
        }
        .desc {
          @extend .single-line-text;
          height: 29px;
          font-size: 24px;
          font-family: SFUIText-Medium, SFUIText;
          font-weight: 500;
          color: #f5e5ff;
          line-height: 29px;
        }
      }
    }
  }
  .board-light {
    width: 738px;
    height: 738px;
    position: absolute;
    left: 6px;
    top: 227px;
    background-repeat: no-repeat;
    background-position: center;
    background-size: 100% 100%;
  }
  .board-light-1 {
    @extend .board-light;
    background-image: url("./assets/board_light_1_ic.png");
  }
  .board-light-2 {
    @extend .board-light;
    background-image: url("./assets/board_light_2_ic.png");
  }
  .btn-layout {
    width: 750px;
    height: 144px;
    top: 983px;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    position: absolute;
    .raffle-ticket-btn {
      width: 308px;
      height: 108px;
      position: relative;
      margin-left: 20px;
      background-repeat: no-repeat;
      background-position: center;
      background-size: 100% 100%;
      background-image: url("./assets/raffle_ticket_btn_ic.png");
      .raffle-ticket-icon {
        width: 121px;
        height: 120px;
        position: absolute;
        left: -16px;
        top: -12px;
      }
      .ticket-layout {
        position: absolute;
        left: 129px;
        top: 19px;
        height: 63px;
        width: 180px;
        display: flex;
        flex-direction: row;
        align-items: baseline;
        .raffle-ticket-num {
          height: 63px;
          font-size: 52px;
          font-family: SFUIText-Bold, SFUIText;
          font-weight: bold;
          color: #ffffff;
          line-height: 63px;
        }
        .ticket-text {
          height: 31px;
          font-size: 26px;
          font-family: SFUIText-Regular, SFUIText;
          font-weight: 400;
          color: #ffffff;
          line-height: 31px;
          margin-left: 4px;
        }
      }
    }
    .lottery-btn {
      width: 432px;
      height: 144px;
      background-repeat: no-repeat;
      background-position: center;
      background-size: 100% 100%;
      background-image: url("./assets/lottery_btn_normal_ic.png");
      position: relative;
      .btn-text {
        width: 154px;
        height: 50px;
        font-size: 42px;
        font-family: SFUIText-Bold, SFUIText;
        font-weight: bold;
        color: #f75e1c;
        line-height: 50px;
        position: absolute;
        display: inline;
        top: 34px;
        left: 50%;
        transform: translateX(-50%);
      }
    }
    .lottery-btn:active {
      background-image: url("./assets/lottery_btn_pressed_ic.png");
    }
  }
  .rule-placeholder {
    width: 750px;
    height: 70px;
    position: absolute;
    top: 1136px;
  }
  .rule-layout {
    width: 750px;
    height: 70px;
    background-repeat: no-repeat;
    background-position: center;
    background-size: 100% 100%;
    background-image: url("./assets/rule_bg.png");
    position: fixed;
    bottom: 0;
    .rule-text {
      @extend .single-line-text;
      height: 40px;
      font-size: 28px;
      font-family: PingFangSC-Regular, PingFang SC;
      font-weight: 400;
      color: #ffffff;
      line-height: 40px;
      position: absolute;
      top: 50%;
      left: 50%;
      display: inline;
      transform: translateX(-50%) translateY(-50%);
    }
  }
}
</style>