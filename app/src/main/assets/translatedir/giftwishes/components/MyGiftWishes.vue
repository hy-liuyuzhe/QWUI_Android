<template>
  <div class="my-gift-wishes-root-v-layout">
    <div class="my-gift-wishes-v-layout">
      <div class="my-gift-wishes-content-v-layout">
        <template v-if="hasGiftWishes">
          <div class="gifts-want-v-layout">
            <span class="gifts-want-today-text-view">{{
              $t("gift_wishes_gifts_want_today")
            }}</span>
            <div class="gifts-want-h-layout">
              <gift-wish-item
                class="gift-wish-item"
                v-for="(item, index) in giftWishes"
                :key="index"
                :gift="item"
              ></gift-wish-item>
            </div>
          </div>
        </template>
        <template v-else>
          <div class="select-gifts-want-v-layout">
            <span class="select-gifts-want-today-text-view">{{
              $t("gift_wishes_set_gifts_want_today")
            }}</span>
            <div class="select-gifts-h-layout">
              <gift-wish-item
                class="select-gift-wish-item"
                v-for="(item, index) in giftWishes"
                :key="index"
                :gift="item"
                v-bind:isSelected="isGiftSelect(item.giftId)"
                @click.native="selectGiftItemClick(item)"
              ></gift-wish-item>
            </div>
          </div>
        </template>
      </div>
      <span
        class="finished-button"
        v-show="hasGiftWishes == false"
        @click="onFinishBtnClick"
        >{{ $t("common_finish") }}</span
      >
    </div>
    <van-image
      class="left-lantern-image-view"
      :src="require('../assets/gift_wishes_lantern.png')"
    ></van-image>
    <van-image
      class="right-lantern-image-view"
      :src="require('../assets/gift_wishes_lantern.png')"
    ></van-image>
    <span class="wish-title-text-view">{{ $t("gift_wishes_wish") }}</span>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import GiftWishItem from "./GiftWishItem";
import api from "../api/index";
import _ from "lodash";
import { getMyUid, getJoinedRoomId } from "../../../common/bridge/index";

Vue.use(VanImage);
export default {
  name: "MyGiftWishes",
  data() {
    return {
      giftWishes: [],
      hasGiftWishes: false,
      selectedGiftIds: [],
    };
  },
  created() {
    this.getUserRamadanInfo();
  },
  components: {
    "gift-wish-item": GiftWishItem,
  },
  methods: {
    selectGiftItemClick(gift) {
      if (
        this.selectedGiftIds.length == 4 &&
        !this.selectedGiftIds.includes(gift.giftId)
      ) {
        return;
      }
      if (this.selectedGiftIds.includes(gift.giftId)) {
        var index = this.selectedGiftIds.indexOf(gift.giftId);
        if (index !== -1) {
          this.selectedGiftIds.splice(index, 1);
        }
      } else {
        this.selectedGiftIds.push(gift.giftId);
      }
    },
    isGiftSelect(giftId) {
      return this.selectedGiftIds.includes(giftId);
    },
    async getUserRamadanInfo() {
      const uid = await getMyUid();
      const res = await api.getUserRamadanInfo(uid);
      if (res.code === 200) {
        let data = res.data;
        let unSetGiftWishes = _.filter(data.giftWishes, function(o) {
          return o.status == 0;
        });
        if (data.status == 0) {
          this.hasGiftWishes = false;
          this.giftWishes = unSetGiftWishes;
        } else {
          let giftWishes = _.filter(data.giftWishes, function(o) {
            return o.status != 0;
          });
          this.hasGiftWishes = true;
          this.giftWishes = giftWishes;
        }
      }
    },
    async onFinishBtnClick() {
      if (this.selectedGiftIds.length <= 0 || this.selectedGiftIds.length > 4) {
        return;
      }
      let joinedRoomId = await getJoinedRoomId();
      const res = await api.setUserRamadanInfo({
        giftIds: this.selectedGiftIds,
        roomId: joinedRoomId,
      });
      if (res.code === 200) {
        this.getUserRamadanInfo();
      }
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include v-layout(my-gift-wishes-root) {
  width: 750px;
  position: relative;
  @include v-layout(my-gift-wishes) {
    width: 692px;
    @include v-layout(my-gift-wishes-content) {
      border-left: 0px;
      border-top: 120px solid transparent;
      border-bottom: 85px solid transparent;
      border-right: 0px;
      border-image: url("../assets/gift_wishes_my_wish_content.png") 120 0 85 0
        fill;
      @include v-layout(select-gifts-want) {
        width: 692px;
        @include text-view(
          select-gifts-want-today,
          $font-size: 28px,
          $line-height: 34px,
          $font-color: #ffffff,
          $font-weight: $--font-weight-bold,
          $text-align: center
        ) {
          width: 560px;
        }
        @include h-layout(select-gifts, $align: start) {
          width: 582px;
          flex-wrap: wrap;
          margin-top: 9px;
          .select-gift-wish-item {
            margin: 27px 10px 0px 10px;
          }
        }
      }
    }
    @include button(finished, $width: 320px, $height: 80px, $text-size: 32px) {
      margin-top: -60px;
      line-height: 32px;
      padding-top: 16px;
      background-image: url("../assets/gift_wishes_yellow_btn.png");
      background-size: 100% 100%;
    }
    @include v-layout(gifts-want) {
      width: 692px;
      @include text-view(
        gifts-want-today,
        $font-size: 28px,
        $line-height: 34px,
        $font-color: #ffffff,
        $font-weight: $--font-weight-bold,
        $text-align: center
      ) {
        width: 560px;
      }
      @include h-layout(gifts-want) {
        margin-top: 32px;
        width: 616px;
        padding-bottom: 10px;
        .gift-wish-item {
          margin: 0px 5px;
        }
      }
    }
  }

  .left-lantern-image-view {
    position: absolute;
    left: 0px;
    top: 36px;
    width: 76px;
    height: 176px;
  }
  .right-lantern-image-view {
    position: absolute;
    right: 0px;
    top: 36px;
    width: 76px;
    height: 176px;
  }
  @include text-view(
    wish-title,
    $font-size: 40px,
    $line-height: 48px,
    $font-color: #fff8e0,
    $font-weight: $--font-weight-bold,
    $text-align: center
  ) {
    width: 750px;
    position: absolute;
    left: 0px;
    top: 28px;
  }
}
</style>
