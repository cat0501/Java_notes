<template>
    <div class="app-container">
        <p>HIVE表级血缘关系</p>
        <div class="mermaid"></div>
    </div>
</template>

<script>
import api from '@/api/govern/govern';
import mermaid from 'mermaid';

export default {
    mounted() {
        mermaid.initialize({ startOnLoad: false });
        api.getLineage().then(response => {
            let element = document.querySelector(".mermaid");
            let insertSvg = (svgCode, _) => {
                element.innerHTML = svgCode;
            };
            mermaid.render("mermaid", response, insertSvg);
        })
    }
};
</script>