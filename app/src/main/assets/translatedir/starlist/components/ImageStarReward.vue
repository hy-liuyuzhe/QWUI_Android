<template>
    <div id="star-reward-container">
        <div class="award-top-light"></div>
        <div class="award-title">
            <span class="award-text">{{ $t('award') }}</span>
        </div>
        <div class="award-container">
            <img class="reward-img" :src="rewardImage">
        </div>
    </div>
</template>
<script>
import RankReward from './RankReward';
import { RANK_LIST_INDEX, RANK_TYPE } from "../constant/constant";

export default {
    name: "ImageStarReward",
    props: {
        type: Number,
    },
    computed: {
        rewardImage() {
            const languageCode = this.$i18n.locale
            if (this.type == RANK_TYPE.RANK_TYPE_STAR) {
                if (languageCode == "ar") {
                    return require('../assets/star_reward_ar.png')
                } else {
                    return require('../assets/star_reward_en.png')
                }
            } else {
                if (languageCode == "ar") {
                    return require('../assets/rich_reward_ar.png')
                } else {
                    return require('../assets/rich_reward_en.png')
                }
            }
        },
    }
}
</script>
<style lang="scss" scoped>
#star-reward-container {
    width: 686px;
    display: flex;
    flex-direction: column;
    align-items: center;
}
.award-container {
    width: 686px;
    background: linear-gradient(135deg, #282019 0%, #2E281C 100%);
    border: 2px solid #DAB25E;
    border-radius: 32px;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 30px;
    padding-bottom: 48px;
    .reward-img {
        width: 600px;
        margin-top: 103px;
    }
}
.award-title {
    width: 400px;
    height: 94px;
    background-image: url("../assets/star_banner_title_bg_new.png");
    background-size: 100% 100%;
    text-align: center;
    position: absolute;
}
.award-text {
    position: relative;
    top: 25px;
    margin: auto;
    font-size: 30px;
    font-family: SFUIText-Medium, SFUIText;
    font-weight: 600;
    color: #DDD8B2;
    line-height: 36px;
}
.award-top-light {
    width: 686px;
    height: 240px;
    background-image: url("../assets/star_reward_top_bg.png");
    background-size: 100% 100%;
    position: absolute;
    top: 30px;
}
</style>