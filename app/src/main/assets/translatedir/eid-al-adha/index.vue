<template>
  <div class="we-page">
    <div class="page-backgroud" />
    <van-image class="top-bg-image-view" :src="topBgImgSrc" />
    <div class="content-v-layout">
      <navigation-bar
        :type="navigationStyle"
        @on-back-click="onNavBackClick($event)"
      />
      <item-layout class="margin-top-432px" :title="activityTime">
        <div class="gifts-v-layout">
          <span class="desc-text-view">
            {{ $t("eid_al_adha_activity_gifts_for_all_desc") }}
          </span>
          <!-- <div class="gifts-grid-layout">
            <gift-item-view
              v-for="(gift, index) in giftConfigInfos"
              :key="index"
              :giftInfo="gift"
            />
          </div> -->
        </div>
      </item-layout>
      <item-layout
        class="margin-top-20px"
        :title="$t('eid_al_adha_shared_prize_pool')"
      >
        <div class="prize-pool-v-layout">
          <span class="desc-text-view">
            {{ $t("eid_al_adha_shared_prize_pool_desc") }}
          </span>
          <div class="prize-pool-status-v-layout">
            <van-image
              class="prize-pool-icon-image-view"
              :src="require('./assets/eid_al_adha_award_pool_ic.png')"
            />
            <span class="total-coins-text-view">
              {{
                $t("eid_al_adha_shared_prize_pool_total_coins", {
                  coins: totalCoins,
                })
              }}
            </span>
            <span class="total-people-text-view">
              {{
                $t("eid_al_adha_shared_prize_pool_total_people", {
                  num: totalNum,
                })
              }}
            </span>
          </div>
          <span class="rule-button" @click="onRuleCick">
            {{ $t("common_rule") }}
          </span>
        </div>
      </item-layout>
      <item-layout class="margin-top-20px" :title="$t('eid_al_adha_award')">
        <div class="award-grid-layout">
          <award-item-view
            v-for="(award, index) in rewardInfos"
            :key="index"
            :awardInfo="award"
          />
        </div>
      </item-layout>
      <item-layout
        :class="['margin-bottom-154px', 'margin-top-20px']"
        :title="$t('eid_al_adha_contribution_list')"
      >
        <div class="contribution-list-v-layout">
          <span class="desc-text-view">
            {{ $t("eid_al_adha_contribution_list_desc") }}
          </span>
          <div class="list-v-layout">
            <rank-item-view
              v-for="(rank, index) in userCorbanInfos"
              :key="index"
              :rankInfo="rank"
            />
          </div>
        </div>
      </item-layout>
      <div v-if="myCorbanInfo" class="self-rank-h-layout">
        <div class="start-h-layout">
          <span class="rank-num-text-view">{{ selfRankText }}</span>
          <van-image
            class="avatar-image-view"
            :src="myCorbanInfo.avatar"
            round
          />
          <span class="name-text-view">{{ myCorbanInfo.name }}</span>
        </div>
        <div class="end-h-layout">
          <span class="glod-coins-num-text-view">{{ myCorbanInfo.coins }}</span>
          <van-image
            class="glod-coins-icon-image-view"
            :src="require('./../common/assets/common_coin_88_ic.png')"
          />
        </div>
      </div>
    </div>
    <van-popup v-model="showGetAwardsDialog">
      <get-awards-dialog
        :awardInfos="myRewardInfos"
        @on-ok-click="onGetAwardDialogOKClick"
      />
    </van-popup>
    <van-popup v-model="showRuleDialog">
      <rule-dialog
        :rankingCoinsInfo="rankingCoinsInfo"
        @on-ok-click="onRuleDialogOKClick"
      />
    </van-popup>
  </div>
</template>
<script>
import _ from "lodash";
import moment from "moment";
import Vue from "vue";
import { Image as VanImage, Toast, Popup } from "vant";
import { hideNavigationBar, closeWindow } from "../../common/bridge";
import { NAVIGATION_BAR_STYLE } from "@/common/constant/index";
import NavigationBar from "@/common/components/NavigationBar";
import ItemLayout from "@/eid-al-adha/components/ItemLayout";
// import GiftItemView from "@/eid-al-adha/components/GiftItemView";
import AwardItemView from "@/eid-al-adha/components/AwardItemView";
import RankItemView from "@/eid-al-adha/components/RankItemView";
import GetAwardsDialog from "@/eid-al-adha/components/GetAwardsDialog";
import RuleDialog from "@/eid-al-adha/components/RuleDialog";
import api from "./api/index";
import { RES_COMMON_CODE } from "../../common/network/constant";

Vue.use(VanImage);
Vue.use(Toast);
Vue.use(Popup);

export default {
  name: "EidAlAdha",
  components: {
    "navigation-bar": NavigationBar,
    "item-layout": ItemLayout,
    // "gift-item-view": GiftItemView,
    "award-item-view": AwardItemView,
    "rank-item-view": RankItemView,
    "get-awards-dialog": GetAwardsDialog,
    "rule-dialog": RuleDialog,
  },
  data() {
    return {
      navigationStyle: NAVIGATION_BAR_STYLE.LIGHT,
      startTime: 0,
      endTime: 0,
      giftConfigInfos: [],
      myCorbanInfo: null,
      myRewardInfos: [],
      rankingCoinsInfo: null,
      rewardInfos: [],
      totalCoins: 0,
      totalNum: 0,
      userCorbanInfos: [],
      showRuleDialog: false,
      showGetAwardsDialog: false,
    };
  },
  created() {
    hideNavigationBar();
    this.getEidAlAdhaActivityInfo();
  },
  methods: {
    onNavBackClick() {
      window.history.length > 1 ? this.$router.go(-1) : closeWindow();
    },
    onRuleCick() {
      if (_.isNil(this.rankingCoinsInfo)) {
        return;
      }

      this.showRuleDialog = true;
    },
    onRuleDialogOKClick() {
      this.showRuleDialog = false;
    },
    onGetAwardDialogOKClick() {
      this.showGetAwardsDialog = false;
    },
    async getEidAlAdhaActivityInfo() {
      const { code, data } = await api.getEidAlAdhaActivityInfo();
      if (code !== RES_COMMON_CODE.SUCCESS) {
        Toast(this.$t("common_failed_with_errorcode", { code }));
        return;
      }

      const {
        startTime,
        endTime,
        giftConfigInfos,
        myCorbanInfo,
        myRewardInfos,
        rankingCoinsInfo,
        rewardInfos,
        totalCoins,
        totalNum,
        userCorbanInfos,
      } = data;
      this.startTime = startTime;
      this.endTime = endTime;
      this.giftConfigInfos = giftConfigInfos;
      this.myCorbanInfo = myCorbanInfo;
      this.myRewardInfos = myRewardInfos;
      this.rankingCoinsInfo = rankingCoinsInfo;
      this.rewardInfos = rewardInfos;
      this.totalCoins = totalCoins;
      this.totalNum = totalNum;
      this.userCorbanInfos = userCorbanInfos;

      const currentTime = new Date().getTime();
      if (currentTime > endTime) {
        if (_.isEmpty(myRewardInfos)) {
          Toast(this.$t("common_activity_end"));
        } else {
          this.showGetAwardsDialog = true;
        }
      }
    },
  },
  computed: {
    topBgImgSrc() {
      let languageCode = this.$i18n.locale;
      if (languageCode === "ar") {
        return require(`./assets/eid_al_adha_ar_top_bg.png`);
      }

      if (languageCode === "zh" || languageCode === "zh-Hans") {
        return require(`./assets/eid_al_adha_zh_top_bg.png`);
      }

      return require(`./assets/eid_al_adha_en_top_bg.png`);
    },
    activityTime() {
      return `${moment(this.startTime).format("YYYY.MM.DD")}-${moment(
        this.endTime
      ).format("MM.DD")}`;
    },
    selfRankText() {
      const corbanInfo = this.myCorbanInfo;
      if (corbanInfo) {
        return corbanInfo.ranking <= 0 ? "-" : corbanInfo.ranking;
      }

      return "-";
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
  position: relative;
  @include backgroud(page, linear-gradient(180deg, #ffa32e 0%, #ffa32a 100%));
  @include image-view(top-bg, 750px, 680px) {
    position: absolute;
    top: 0;
    left: 0;
  }
  @include v-layout(content) {
    width: 100%;
    .margin-top-432px {
      margin-top: 432px;
    }
    .margin-top-20px {
      margin-top: 20px;
    }
    .margin-bottom-154px {
      margin-bottom: 154px;
    }
    @include v-layout(gifts) {
      width: 100%;
      padding: 0px 40px;
      @include text-view(
        desc,
        $font-size: 28px,
        $line-height: 34px,
        $font-color: #724637,
        $font-weight: $--font-weight-regular
      ) {
        margin-top: 24px;
      }
      @include grid-layout(gifts, 180px 180px 180px, 248px, 24px, 24px) {
        margin-top: 24px;
      }
    }
    @include v-layout(prize-pool) {
      width: 100%;
      @include text-view(
        desc,
        $font-size: 28px,
        $line-height: 34px,
        $font-color: #724637,
        $font-weight: $--font-weight-regular
      ) {
        padding: 0px 40px;
        margin-top: 24px;
      }
      @include v-layout(prize-pool-status) {
        width: 622px;
        margin-top: 24px;
        background: #ffe6b5;
        border-radius: 12px;
        @include image-view(prize-pool-icon, 622px, 320px);
        @include text-view(
          total-coins,
          $font-size: 32px,
          $line-height: 38px,
          $font-color: #ff4700,
          $font-weight: $--font-weight-bold
        ) {
          margin-top: 16px;
        }
        @include text-view(
          total-people,
          $font-size: 24px,
          $line-height: 29px,
          $font-color: rgba(114, 70, 55, 0.6),
          $font-weight: $--font-weight-bold
        ) {
          margin-top: 6px;
          margin-bottom: 16px;
        }
      }
      @include button(
        rule,
        $width: 280px,
        $height: 60px,
        $text-size: 28px,
        $text-color: $--color-white,
        $border-radius: 24px,
        $background: $--color-transparent
      ) {
        margin-top: 16px;
        background-image: url("./assets/eid_al_adha_button_bg.png");
        background-repeat: no-repeat;
        background-size: cover;
      }
    }
    @include grid-layout(award, 280px 280px, 406px, 24px, 24px) {
      margin-top: 24px;
    }
    @include v-layout(contribution-list) {
      width: 100%;
      margin-top: 24px;
      @include text-view(
        desc,
        $font-size: 24px,
        $line-height: 29px,
        $font-color: rgba(114, 70, 55, 0.6),
        $font-weight: $--font-weight-regular
      ) {
        padding: 0px 40px;
      }
      @include v-layout(list) {
        width: 100%;
        margin-top: 24px;
      }
    }
    @include h-layout(self-rank) {
      width: 100%;
      height: 130px;
      position: fixed;
      bottom: 0;
      background-image: url("./assets/eid_al_adha_self_bg.png");
      background-repeat: no-repeat;
      background-size: cover;
      justify-content: space-between;
      @include h-layout(start) {
        @include text-view(
          rank-num,
          $font-size: 32px,
          $line-height: 37px,
          $font-color: $--color-white,
          $font-weight: $--font-weight-bold,
          $text-align: center
        ) {
          width: 114px;
        }
        @include image-view(avatar, 70px, 70px);
        @include text-view(
          name,
          $font-size: 28px,
          $line-height: 34px,
          $font-color: $--color-white,
          $font-weight: $--font-weight-medium,
          $line-number: 1
        ) {
          width: 350px;
          @include compat.margin-start(24px);
        }
      }
      @include h-layout(end) {
        @include compat.margin-end(30px);
        @include text-view(
          glod-coins-num,
          $font-size: 26px,
          $line-height: 31px,
          $font-color: $--color-white,
          $font-weight: $--font-weight-regular,
          $line-number: 1
        );
        @include image-view(glod-coins-icon, 28px, 28px) {
          @include compat.margin-start(4px);
        }
      }
    }
  }
}
</style>