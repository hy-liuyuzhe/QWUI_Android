<template>
    <div id="start-list-page">
        <image-star-reward
            v-bind:type="type">
        </image-star-reward>
        <rank-list v-bind:type="type"></rank-list>
    </div>
</template>
<script>
import ImageStarReward from './ImageStarReward';
import RankList from './RankList';
import { RANK_TYPE } from "../constant/constant";

export default {
    name: "StarListPage",
    components: {
        "image-star-reward": ImageStarReward,
        "rank-list": RankList
    },
    props: {
        type: Number,
        startTime: Number,
        endTime: Number
    },
    computed: {
        rewards() {
            if (this.type == RANK_TYPE.RANK_TYPE_STAR) {
                return this.$store.state.starlist.starRewardList;
            } else {
                return this.$store.state.starlist.richRewardList;
            }
        },
    }
}
</script>
<style lang="scss" scoped>
#rank-list-container {
    width: 686px;
    position: absolute;
    display: flex;
    flex-direction: column;
}
</style>