<template>
    <div class="frame-right-container">
        <van-image :src="require('../assets/lady_frame_reward_ic.png')" class="reward-img"></van-image>
        <div class="frame-value-desc">
            <van-image :src="require('../../common/assets/common_coin_48_ic.png')" class="coin-img"></van-image>
            <span class="frame-value">70000</span>
            <span class="frame-days">{{ $t("lady_days", { days: 7 }) }}</span>
        </div>
        <span class="frame-right-desc">{{ $t('lady_frame_right', { fans: fansCount }) }}</span>
    </div>
</template>
<script>
import Vue from 'vue';
import { Image as VanImage } from 'vant';
import { LADY_CONSTANTS } from "../constant/constant";

Vue.use(VanImage);
export default {
    name: "FrameRight",
    computed: {
        fansCount() {
            return LADY_CONSTANTS.FANS_COUNT
        }
    }
}
</script>
<style lang="scss" scoped>
.frame-right-container {
    width: 702px;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-top: 70px;
    padding-bottom: 60px;
}
.reward-img {
    width: 280px;
    height: 280px;
}
.frame-value-desc {
    margin-top: 16px;
    display: flex;
    flex-direction: row;
    align-items: center;
    .coin-img {
        width: 36px;
        height: 36px;
    }
    .frame-value {
        font-size: 32px;
        font-family: SFUIText-Bold, SFUIText;
        font-weight: bold;
        color: #C54D23;
        margin-left: 4px;
    }
    .frame-days {
        font-size: 32px;
        font-family: SFUIText-Bold, SFUIText;
        font-weight: bold;
        color: #C54D23;
        margin-left: 16px;
    }
}
.frame-right-desc {
    margin-top: 30px;
    width: 622px;
    font-size: 28px;
    font-family: SFUIText-Regular, SFUIText;
    font-weight: 400;
    color: #C54D23;
    line-height: 34px;
    text-align: start;
}
</style>