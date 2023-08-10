<template>
    <div>
        <div :class="[rankNo1 ? 'high-rank-reward-content' : 'low-rank-reward-content']">
            <div :class="[rankNo1 ? 'hight-rank-bottom' : 'low-rank-bottom']"></div>
            <div class="rank-reward-preview">
                <van-image :src="rewardInfo.icon" class="reward-img"></van-image>
            </div>
            <van-image 
                class="reward-diamond-perview" 
                :src="rewardDiamond"
                v-show="false">
            </van-image>
            <p class="rank-reward-days">{{ rewardDays }}</p>
            <div :class="[rankNo1 ? 'hight-rank-reward-diamond' : 'low-rank-reward-diamond']" v-show="showDiamondReward">
                <van-image class="diamond-icon" :src="require('../assets/star_diamond.png')"></van-image>
                <span class="diamond-text">{{ $t("diamond_count", {count: rewardInfo.diamond}) }}</span>
            </div>
            <van-image class="rank-medal" :src="rankMedal"></van-image>
        </div>
    </div>
</template>
<script>
import Vue from 'vue';
import { Image as VanImage } from 'vant';

Vue.use(VanImage);
export default {
    name: "RankReward",
    props: {
        type: Number,
        rank: Number,
        rewardInfo: {
            icon: String,
            days: Number,
            diamond: Number
        }
    },
    computed: {
        showDiamondReward() {
            return this.rewardInfo.diamond > 0;
        },
        rankNo1() {
            return this.rank == 1;
        },
        rankMedal() {
            if (this.rank == 1) {
                return require('../assets/star_rank_1_medal_new.png')
            } else if (this.rank == 2) {
                return require('../assets/star_rank_2_medal_new.png')
            } else {
                return require('../assets/star_rank_3_medal_new.png')
            }
        },
        rewardDays() {
            if (this.rewardInfo.days > 0) {
                return this.$t("reward_car", { days: this.rewardInfo.days });
            } else {
                return "";
            }
        },
        rewardDiamond() {
            if (this.rank == 1) {
                return require('../assets/star_reward_diamond_1.png')
            } else if (this.rank == 2) {
                return require('../assets/star_reward_diamond_2.png')
            } else {
                return require('../assets/star_reward_diamond_3.png')
            }
        }
    }
}
</script>
<style lang="scss" scoped>
.rank-reward-content {
    width: 220px;
}
.high-rank-reward-content {
    @extend .rank-reward-content;
    height: 372px;
}
.low-rank-reward-content {
    @extend .rank-reward-content;
    height: 332px;
}
.rank-bottom {
    width: 220px;
    background: linear-gradient(180deg, #6C5840 0%, rgba(99, 84, 57, 0.1) 100%);
    border-radius: 16px;
    position: absolute;
    bottom: 0;
    left: 0px;
    margin: auto;
}
.low-rank-bottom {
    @extend .rank-bottom;
    height: 223px;
}
.hight-rank-bottom {
    @extend .rank-bottom;
    height: 258px;
    background: linear-gradient(180deg, #D8A363 0%, rgba(99, 84, 57, 0.1) 100%);
    border-radius: 16px 16px 0px 0px;
    /*border-radius: 32px;*/
}
.rank-medal {
    width: 112px;
    height: 40px;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    margin: auto;
}
.rank-reward-preview {
    width: 180px;
    height: 180px;
    background-image: url('../assets/star_reward_item_bg_new.png');
    background-size: 100% 100%;
    position: absolute;
    top: 20px;
    left: 0;
    right: 0;
    margin: auto;
}
.reward-img {
    width: 160px;
    height: 160px;
    top: 10px;
    left: 0;
    right: 0;
    position: absolute;
    margin: auto;
}
.reward-diamond-perview {
    width: 160px;
    height: 160px;
    position: absolute;
    top: 30px;
    left: 0;
    right: 0;
    margin: auto;
}
.rank-reward-days {
    height: 31px;
    font-size: 26px;
    font-family: SFUIText-Medium, SFUIText;
    font-weight: 500;
    color: #FFFFFF;
    line-height: 31px;
    position: absolute;
    top: 218px;
    left: 0;
    right: 0;
    margin: auto; 
}
.rank-reward-diamond {
    height: 36px;
    width: 220px;
    position: absolute;
    top: 180px;
    left: 0px;
    right: 0px;
    margin: auto;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
}
.hight-rank-reward-diamond {
    @extend .rank-reward-diamond;
    bottom: 80px;
}
.low-rank-reward-diamond {
    @extend .rank-reward-diamond;
    bottom: 40px;
}
.diamond-icon {
    width: 30px;
    height: 30px;
}
.diamond-text {
    font-size: 26px;
    font-family: SFUIText-Medium, SFUIText;
    font-weight: 500;
    color: #FFFFFF;
    margin-left: 9px;
}
</style>