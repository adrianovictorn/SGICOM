<script>
	import { postApi } from "$lib/api";
	import Content from "$lib/components/Content.svelte";


    let nomeCidade = $state("")
    let codigoIBGE = $state("")


    async function cadastrarCidade() {
        const payload = {
            nome: nomeCidade,
            codigoIbge: codigoIBGE
        }

        console.log(payload)
        try {
            const res = await postApi("/api/cidade/cadastrar", payload)
            alert("Nova cidade Cadastrada com Sucesso !");
            limparCampos()

        } catch (error) {
            alert("Erro ao Cadastrar nova Cidade")
        }
    }

    function limparCampos(){
        nomeCidade = ""
        codigoIBGE = ""
    }
</script>


<svelte:head>
    <title>Cadastrar Cidade</title>
</svelte:head>

<Content> 
    <section class="p-4">
        <form action="" class="bg-white rounded-lg  p-4 " onsubmit={cadastrarCidade}>
            <div class="grid g grid-cols-3 gap-4  w-full">
                <div class=" flex flex-col">
                    <label for="">Nome da Cidade <span class="text-red-500">*</span></label>
                    <input type="text" placeholder="Nome da Cidade" bind:value={nomeCidade}>
                </div>
                <div class=" flex flex-col">
                    <label for="">Código IBGE <span class="text-red-500">*</span></label>
                    <input type="text" placeholder="Código do IBGE" bind:value={codigoIBGE}>
                </div>
                <div class="flex flex-col mt-6">
                    <button type="submit" class="bg-green-400 hover:bg-green-800 text-white cursor-pointer rounded-lg p-2">Cadastrar</button>
                </div>
            </div>
        </form>
    </section>
</Content>