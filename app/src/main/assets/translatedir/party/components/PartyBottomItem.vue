<template>
  <div class="party-bottom-container" v-show="showRoot">
    <div class="create-party-container" v-show="showCreateButton">
      <div class="top-arrows">
        <img class="left-arrow" :src="leftArrow" />
        <img class="right-arrow" :src="rightArrow" />
      </div>
      <div class="create-button" @click="onCreatePartyClick">
        <span class="create-text">{{ $t("party_create") }}</span>
      </div>
      <span class="party-activity-limit">{{
        $t("party_activity_limit", {
          limit: activityConfig.activityNumLimit,
        })
      }}</span>
    </div>
    <div class="my-rank-info-container" v-if="rankInfo != undefined">
      <party-rank-item
        v-bind:rankInfo="rankInfo"
        v-bind:rank="rankInfo.rank"
      ></party-rank-item>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import { Toast } from "vant";
import PartyRankItem from "../components/PartyRankItem";
import { dispatchDeeplink } from "../../../common/bridge/index";

Vue.use(Toast);
export default {
  name: "PartyBottomItem",
  props: {
    activityConfig: Object,
    rankInfo: Object,
  },
  computed: {
    showCreateButton() {
      if (this.activityConfig) {
        if (this.rankInfo != undefined) {
          return false;
        }
        return this.activityConfig.hasNewActivityChance == true;
      }
      return true;
    },
    showMyRank() {
      return this.rankInfo != undefined;
    },
    showRoot() {
      return this.showCreateButton || this.showMyRank;
    },
    leftArrow() {
      return this.$i18n.locale == "ar"
        ? require("../assets/party_arrow_right.png")
        : require("../assets/party_arrow_left.png");
    },
    rightArrow() {
      return this.$i18n.locale == "ar"
        ? require("../assets/party_arrow_left.png")
        : require("../assets/party_arrow_right.png");
    },
  },
  components: {
    "party-rank-item": PartyRankItem,
  },
  methods: {
    async onCreatePartyClick() {
      if (this.activityConfig && !this.activityConfig.hasNewActivityChance) {
        Toast({
          message: this.$t("party_create_limit"),
          duration: 3000,
        });
        return;
      }
      dispatchDeeplink({
        deeplink:
          "wenext://wayak/party/edit_party?extra_edit_type=0&extra_activity_type=2",
        path: "/party/edit_party",
      });
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
.party-bottom-container {
  width: 750px;
  height: 142px;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-image: url("../assets/party_bottom_item_bg.png");
  background-size: 100% 100%;
}
.create-party-container {
  width: 750px;
  height: 142px;
  display: flex;
  flex-direction: column;
  align-items: center;
  .top-arrows {
    width: 750px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    .left-arrow {
      width: 68px;
      height: 64px;
      @include compat.margin-start(88px);
    }
    .right-arrow {
      width: 68px;
      height: 64px;
      @include compat.margin-end(88px);
    }
  }
  .create-button {
    width: 400px;
    height: 80px;
    background: linear-gradient(180deg, #f46297 0%, #f3b9ff 100%);
    border-radius: 39px;
    flex-direction: column;
    align-items: center;
    margin-top: -40px;
    .create-text {
      width: 359px;
      height: 80px;
      font-size: 30px;
      font-family: SFUIText-Bold, SFUIText;
      font-weight: bold;
      color: #ffffff;
      line-height: 80px;
    }
  }
  .party-activity-limit {
    width: 296px;
    font-size: 24px;
    font-family: SFUIText-Regular, SFUIText;
    font-weight: 400;
    color: rgba(255, 255, 255, 0.5);
    margin-top: 8px;
  }
}
.my-rank-info-container {
  width: 750px;
  height: 142px;
  @include compat.padding-start(30px);
}
</style>