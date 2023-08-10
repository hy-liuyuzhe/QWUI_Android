<template>
  <div class="party-rules-container">
    <span class="top-desc">{{ this.$t("party_top_desc") }}</span>
    <party-section :title="$t('party_reward')">
      <template v-slot:content>
        <div class="rewards-container">
          <div class="reward-imags">
            <van-image
              class="reward-img"
              v-for="(item, index) in ruleRewardImgList"
              :key="index"
              :src="item"
            >
            </van-image>
          </div>
          <div
            class="reward-item"
            v-for="(item, index) in rankRewards"
            :key="index"
            :src="item"
          >
            <div class="reward-item-title-container">
              <img
                class="reward-rank-title-bg"
                :src="require('../assets/party_reward_rank_title_bg.png')"
              />
              <span class="reward-rank-title">{{
                $t("party_reward_rank", { rank: index + 1 })
              }}</span>
            </div>
            <div class="reward-item-content-container">
              <i18n
                path="party_reward_diamond_party_fund"
                tag="span"
                class="reward-item-text"
              >
                <template #diamonds>
                  <span class="reward-highlight-text">{{ item.diamonds }}</span>
                </template>
              </i18n>
              <i18n
                path="party_reward_new_year_popular_room"
                tag="span"
                class="reward-item-text"
              >
                <template #rank>
                  <span class="reward-highlight-text">{{ index + 1 }}</span>
                </template>
              </i18n>
              <span class="reward-item-text">{{
                $t("party_reward_new_year_theme")
              }}</span>
              <i18n
                path="party_reward_room_acceleration"
                tag="span"
                class="reward-item-text"
              >
                <template #times>
                  <span class="reward-highlight-text">{{
                    item.roomGiftSpeedupMultitimes
                  }}</span>
                </template>
              </i18n>
            </div>
          </div>
        </div>
      </template>
    </party-section>
    <party-section :title="$t('rule')">
      <template v-slot:content>
        <div class="rules-container">
          <span class="rules">{{ $t("party_activity_rule1") }}</span>
          <span class="rules">{{ $t("party_activity_rule2") }}</span>
          <span class="rules">{{ $t("party_activity_rule3") }}</span>
          <span class="rules">{{ $t("party_activity_rule4") }}</span>
        </div>
      </template>
    </party-section>
  </div>
</template>
<script>
import Vue from "vue";
import { RuleRewardImgList } from "../constant/constant";
import { Image as VanImage } from "vant";
import PartySection from "../components/PartySection";

Vue.use(VanImage);
export default {
  name: "PartyRules",
  components: {
    "party-section": PartySection,
  },
  props: {
    activityConfig: Object,
  },
  computed: {
    ruleRewardImgList() {
      return RuleRewardImgList;
    },
    rankRewards() {
      return this.activityConfig.rankRewards;
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
.party-rules-container {
  width: 750px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.top-desc {
  width: 620px;
  font-size: 32px;
  font-family: SFUIText-Bold, SFUIText;
  font-weight: bold;
  color: #ff95df;
  line-height: 38px;
  margin-top: 20px;
}
.rewards-container {
  width: 690px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.reward-imags {
  width: 620px;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  margin-top: 87px;
  .reward-img {
    width: 145px;
    height: 145px;
    margin-inline-start: 5px;
    margin-inline-end: 5px;
  }
}
.reward-item {
  width: 690px;
  display: flex;
  flex-direction: column;
  margin-top: 39px;
  .reward-item-title-container {
    width: 202px;
    height: 62px;
    display: flex;
    flex-direction: column;
    @include compat.margin-start(-31px);
    .reward-rank-title-bg {
      width: 202px;
      height: 62px;
    }
    .reward-rank-title {
      width: 83px;
      font-size: 32px;
      font-family: SFUIText-HeavyItalic, SFUIText;
      font-weight: normal;
      color: #ffffff;
      margin-top: -50px;
      @include compat.margin-start(32px);
    }
  }
  .reward-item-content-container {
    width: 650px;
    display: flex;
    flex-direction: column;
    margin-top: 23px;
    @include compat.padding-start(40px);
    .reward-item-text {
      font-size: 26px;
      width: 520px;
      font-family: SFUIText-Regular, SFUIText;
      font-weight: 400;
      color: #ffffff;
      line-height: 31px;
      text-align: start;
      margin-top: 5px;
    }
    .reward-highlight-text {
      font-size: 26px;
      font-family: SFUIText-Regular, SFUIText;
      font-weight: 400;
      color: #fef28f;
      line-height: 31px;
    }
  }
}
.rules-container {
  width: 690px;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 39px;
  .rules {
    width: 610px;
    text-align: start;
    margin-inline-start: 40px;
    margin-inline-end: 40px;
    font-size: 26px;
    font-family: SFUIText-Regular, SFUIText;
    font-weight: 400;
    color: #ffffff;
    line-height: 31px;
    margin-top: 40px;
  }
}
</style>
