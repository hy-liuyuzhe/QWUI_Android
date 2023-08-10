<template>
    <div id="rank-list-container">
        <div class="list-container">
        <div class="pull-list-wrapper">
            <van-list
                class="rank-item-list"
                v-model="loading"
                :finished="finished"
                finished-text="">
                <rank-list-item 
                    v-for="(item, index) in rankItemInfos"
                    :key="index"
                    class="rank-item"
                    v-bind:rankInfo="item"
                    v-bind:rank="index + 1"
                    v-bind:type="type"
                />
            </van-list>
            <div class="bottom-space"></div>
        </div>
        </div>
        <div class="rank-top-light"></div>
        <div class="list-title">
            <span class="rank-text">{{ $t('ranking') }}</span>
        </div>
    </div>
</template>
<script>
import Vue from 'vue';
import { RANK_TYPE } from "../constant/constant";
import RankListItem from './RankListItem';
import { List } from 'vant';

Vue.use(List);
export default {
    name: "RankList",
    props: {
        type: Number
    },
    components: {
        "rank-list-item": RankListItem
    },
    data() {
        return {
            loading: false,
            finished: true,
        }
    },
    created() {
        this.refreshRankList();
    },
    computed: {
        rankItemInfos() {
            if (this.type == RANK_TYPE.RANK_TYPE_STAR) {
                return this.$store.state.starlist.startRankList;
            } else {
                return this.$store.state.starlist.richRankList;
            }
        },
    },
    methods: {
        refreshRankList() {
          this.$store.dispatch("starlist/getRankList", {type: this.type});
        }
    },
}
</script>
<style lang="scss" scoped>
.rank-top-light {
    width: 686px;
    height: 100px;
    background-image: url("../assets/star_rank_top_bg_new.png");
    background-size: 100% 100%;
    position: absolute;
    top: 30px;
}
.list-container {
    width: 686px;
    background: linear-gradient(135deg, #282019 0%, #2E281C 100%);
    border: 32px;
    position: absolute;
    left: 0;
    right: 0;
    top: 30px;
    margin: auto;
}
.list-title {
    width: 400px;
    height: 94px;
    background-image: url("../assets/star_banner_title_bg_new.png");
    background-size: 100% 100%;
    text-align: center;
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    margin: auto;
}
.rank-text {
    position: relative;
    top: 25px;
    margin: auto;
    font-size: 30px;
    font-family: SFUIText-Semibold, SFUIText;
    font-weight: 600;
    color: #DDD8B2;
    line-height: 36px;
}
.pull-list-wrapper {
    width: 686px;
    position: absolute;
    top: 0;
    display: flex;
    flex-direction: column;
}
.rank-item-list {
    width: 686px;
    padding-top: 84px;
    padding-bottom: 24px;
    background: linear-gradient(135deg, #282019 0%, #2E281C 100%);
    border-radius: 32px 32px 32px 32px;
}
.bottom-space {
    height: 160px;
    width: 686px;
}
.rank-item {
    width: 686px;
    height: 128px;
    display: flex;
    flex-direction: row;
    align-items: center;
}
.rank-item {
    width: 686px;
    height: 128px;
    display: flex;
    flex-direction: row;
    align-items: center;
}
</style>