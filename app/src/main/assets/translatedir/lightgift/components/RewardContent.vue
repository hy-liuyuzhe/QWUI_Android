<template>
  <div class="root-layout">
    <div class="avatar-frame-layout">
      <span class="reward-text">x {{ goodsDay }}days</span>
    </div>
    <div class="exp-layout">
      <span class="reward-text">x {{ exp }}days</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "RewardContent",
  props: {
    goodsDay: Number,
    exp: Number,
  },
};
</script>

<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@import "../../common/css/index.scss";

.root-layout {
  display: flex;
  flex-direction: row;
  .item-layout {
    width: 200px;
    height: 220px;
    background-size: 100%;
    background-repeat: no-repeat;
  }
  .avatar-frame-layout {
    @extend .item-layout;
    background-image: url("../assets/lion_king_ic.png");
  }
  .exp-layout {
    @extend .item-layout;
    background-image: url("../assets/exp_ic.png");
    @include compat.margin-start(16px);
  }
  .reward-text {
    @extend .single-line-text;
    position: relative;
    height: 34px;
    font-size: 28px;
    font-family: SFUIText-Semibold, SFUIText;
    font-weight: 600;
    color: #fffac5;
    line-height: 34px;
    top: 172px;
  }
}
</style>