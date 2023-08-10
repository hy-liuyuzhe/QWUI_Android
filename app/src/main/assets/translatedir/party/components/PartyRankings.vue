<template>
  <div class="party-ranking-container">
    <div class="party-hot">
      <img class="hot-img" :src="require('../assets/party_hot.png')" />
      <span class="hot-text">&nbsp;=&nbsp;</span>
      <img class="diamond-img" :src="require('../assets/party_diamond.png')" />
      <span class="hot-text">&nbsp;+ 100*&nbsp;</span>
      <img class="people-img" :src="require('../assets/party_people.png')" />
    </div>
    <span class="hot-desc">{{ $t("party_hot_desc") }}</span>
    <party-section :title="$t('ranking')">
      <template v-slot:content>
        <div class="rank-container">
          <div class="top-ranks-container">
            <div
              class="rank1-container"
              v-show="rank1Info != undefined"
              @click="onAvatarClick(rank1Info.creatorUid)"
            >
              <van-image
                class="rank1-avatar"
                :radius="16"
                :src="rank1Info != undefined ? rank1Info.creatorUrl : ''"
              ></van-image>
              <img
                class="rank1-deco"
                :src="require('../assets/party_deco_1.png')"
              />
            </div>
            <div class="top23-ranks-container">
              <div
                class="rank2-container"
                v-show="rank2Info != undefined"
                @click="onAvatarClick(rank2Info.creatorUid)"
              >
                <van-image
                  class="rank2-avatar"
                  :radius="16"
                  :src="rank2Info != undefined ? rank2Info.creatorUrl : ''"
                ></van-image>
                <img
                  class="rank2-deco"
                  :src="require('../assets/party_deco_2.png')"
                />
              </div>
              <div
                class="rank3-container"
                v-show="rank3Info != undefined"
                @click="onAvatarClick(rank3Info.creatorUid)"
              >
                <van-image
                  class="rank3-avatar"
                  :radius="16"
                  :src="rank3Info != undefined ? rank3Info.creatorUrl : ''"
                ></van-image>
                <img
                  class="rank3-deco"
                  :src="require('../assets/party_deco_3.png')"
                />
              </div>
            </div>
          </div>
          <div class="rank-list">
            <div
              class="rank-item"
              v-for="(item, index) in ranks"
              :key="index"
              :src="item"
            >
              <party-rank-item v-bind:rankInfo="item" v-bind:rank="index + 1">
              </party-rank-item>
              <div class="list-divider"></div>
            </div>
          </div>
        </div>
      </template>
    </party-section>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import { ACTIVITY_TYPE } from "../constant/constant";
import PartySection from "../components/PartySection";
import PartyRankItem from "../components/PartyRankItem";
import {
  dispatchDeeplink,
  isDeeplinkSupport,
} from "../../../common/bridge/index";

Vue.use(VanImage);
export default {
  name: "PartyRankings",
  data() {
    return {
      activityType: ACTIVITY_TYPE.NEW_YEAR,
    };
  },
  components: {
    "party-section": PartySection,
    "party-rank-item": PartyRankItem,
  },
  created() {
    this.refreshRankList();
  },
  computed: {
    ranks() {
      return this.$store.state.party.rankList;
    },
    rank1Info() {
      var rankList = this.ranks;
      if (rankList && rankList.length > 0) {
        return rankList[0];
      }
      return undefined;
    },
    rank2Info() {
      var rankList = this.ranks;
      if (rankList && rankList.length > 1) {
        return rankList[1];
      }
      return undefined;
    },
    rank3Info() {
      var rankList = this.ranks;
      if (rankList && rankList.length > 2) {
        return rankList[2];
      }
      return undefined;
    },
  },
  methods: {
    refreshRankList() {
      this.$store.dispatch("party/getRankList", {
        activityType: this.activityType,
      });
    },
    async onAvatarClick(uid) {
      if (uid == undefined) {
        return;
      }
      var supportResult = await isDeeplinkSupport({ path: "/userProfile" });
      if (supportResult && supportResult.isSupport) {
        window.location.href = "wenext://wayak/userProfile?extra_uid=" + uid;
      }
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
.party-ranking-container {
  width: 750px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.party-hot {
  width: 690px;
  height: 72px;
  background: #1327aa;
  border-radius: 36px;
  border: 2px solid;
  border-image: linear-gradient(
      180deg,
      rgba(73, 75, 183, 1),
      rgba(73, 75, 183, 1)
    )
    2 2;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  margin-top: 20px;
  .hot-img {
    width: 84px;
    height: 40px;
  }
  .diamond-img {
    width: 40px;
    height: 40px;
  }
  .hot-text {
    height: 38px;
    font-size: 32px;
    font-family: SFUIText-Bold, SFUIText;
    font-weight: bold;
    color: #ff95df;
    line-height: 38px;
  }
  .people-img {
    width: 40px;
    height: 40px;
  }
}
.hot-desc {
  width: 488px;
  font-size: 24px;
  font-family: SFUIText-Regular, SFUIText;
  font-weight: 400;
  color: #87a7ef;
  line-height: 29px;
  margin-top: 20px;
}
.rank-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.top-ranks-container {
  display: flex;
  flex-direction: row;
  align-items: center;
  flex-wrap: wrap;
  justify-content: center;
}
.top23-ranks-container {
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  @include compat.margin-start(19px);
}
.rank1-container {
  width: 484px;
  height: 317px;
  background-image: url("../assets/party_stage_1.png");
  background-size: 100% 100%;
  margin-top: 80px;
  display: flex;
  flex-direction: column;
  align-items: center;
  .rank1-avatar {
    width: 180px;
    height: 180px;
    border-radius: 16px;
    border: 1px solid #ee9bfe;
  }
  .rank1-deco {
    width: 236px;
    height: 202px;
    margin-top: -195px;
    z-index: 2;
  }
}
.rank2-container {
  width: 302px;
  height: 142px;
  margin-top: 31px;
  background-image: url("../assets/party_stage_2.png");
  background-size: 100% 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  .rank2-avatar {
    width: 148px;
    height: 148px;
    margin-top: -121px;
    border-radius: 16px;
    border: 1px solid #ee9bfe;
  }
  .rank2-deco {
    width: 200px;
    height: 172px;
    margin-top: -170px;
    z-index: 2;
  }
}
.rank3-container {
  width: 302px;
  height: 142px;
  margin-top: 31px;
  @include compat.margin-start(49px);
  background-image: url("../assets/party_stage_3.png");
  background-size: 100% 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  .rank3-avatar {
    width: 148px;
    height: 148px;
    margin-top: -121px;
    border-radius: 16px;
    border: 1px solid #ee9bfe;
  }
  .rank3-deco {
    width: 200px;
    height: 172px;
    margin-top: -170px;
    z-index: 2;
  }
}
.rank-list {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 689px;
  margin-top: 19px;
  .rank-item {
    display: flex;
    flex-direction: column;
    .list-divider {
      width: 606px;
      height: 1px;
      background: #6446cd;
      @include compat.margin-start(80px);
      margin-top: 1px;
    }
  }
}
</style>