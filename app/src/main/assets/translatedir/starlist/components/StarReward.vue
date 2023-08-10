<template>
    <div id="star-reward-container">
        <div class="award-container">
            <rank-reward  
                class="rank-2-reward" 
                v-bind:rank="2" 
                v-bind:type="type" 
                v-bind:rewardInfo="rewardList[1]">
            </rank-reward>
            <rank-reward  
                class="rank-1-reward" 
                v-bind:rank="1" 
                v-bind:type="type" 
                v-bind:rewardInfo="rewardList[0]">
            </rank-reward>
            <rank-reward  
                class="rank-3-reward" 
                v-bind:rank="3" 
                v-bind:type="type" 
                v-bind:rewardInfo="rewardList[2]">
            </rank-reward>
        </div>
        <div class="award-top-light"></div>
        <div class="award-title">
            <span class="award-text">{{ $t('award') }}</span>
        </div>
    </div>
</template>
<script>
import RankReward from './RankReward';

export default {
    name: "StarReward",
    props: {
        type: Number,
        rewardList: Array
    },
    components: {
        "rank-reward": RankReward
    },
    computed: {
    }
}
</script>
<style lang="scss" scoped>
.award-title {
    width: 400px;
    height: 94px;
    background-image: url("../assets/star_banner_title_bg_new.png");
    background-size: 100% 100%;
    text-align: center;
    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
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
.award-container {
    width: 686px;
    height: 480px;
    background: linear-gradient(135deg, #282019 0%, #2E281C 100%);
    border: 2px solid #DAB25E;
    border-radius: 32px;
    position: absolute;
    left: 0;
    right: 0;
    top: 30px;
    margin: auto;
}
.award-top-light {
    width: 686px;
    height: 240px;
    background-image: url("../assets/star_reward_top_bg.png");
    background-size: 100% 100%;
    position: absolute;
    top: 30px;
}
.rank-2-reward {
    position: absolute;
    left: 13px;
    bottom: 0;
    margin: auto;
}
.rank-1-reward {
    position: absolute;
    left: 233px;
    background: linear-gradient(135deg, #282019 0%, #2E281C 100%);
    bottom: 0;
    margin: auto;
}
.rank-3-reward {
    position: absolute;
    left: 453px;
    bottom: 0;
    margin: auto;
}
</style>