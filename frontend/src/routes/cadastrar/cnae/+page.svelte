<script>
	import { postApi } from "$lib/api";
	import Content from "$lib/components/Content.svelte";

    let codigo = $state("")
    let descricao = $state("")


    async function cadastrarCNAE() {
        const payload ={
            codigo,
            descricao
        }
        try {
            const res = await postApi("/api/cnae/cadastrar", payload)
            alert("CNAE adicionado com sucesso !")
            codigo = ""
            descricao = ""
        } catch (error) {
            alert ("Erro ao cadastrar CNAE")
        }
    }
</script>


<Content>
        <section class="p-4">
        <form action="" class="bg-white rounded-lg  p-4 " onsubmit={cadastrarCNAE}>
            <div class="grid g grid-cols-3 gap-4  w-full">
                <div class=" flex flex-col">
                    <label for="">Código <span class="text-red-500">*</span></label>
                    <input type="text" placeholder="Código de Classificação de Atividade Econômica " bind:value={codigo}>
                </div>
                <div class=" flex flex-col">
                    <label for="">Descrição <span class="text-red-500">*</span></label>
                    <input type="text" placeholder="Descrição de Atividade Econômica" bind:value={descricao}>
                </div>
                <div class="flex flex-col mt-6">
                    <button type="submit" class="bg-green-400 hover:bg-green-800 text-white cursor-pointer rounded-lg p-2">Cadastrar</button>
                </div>
            </div>
        </form>
    </section>
</Content>