<script lang="ts">
	import { getApi, postApi } from "$lib/api";
	import { onMount } from "svelte";

    interface Cidade{
        id: number
        nome: string
        codigoIbge: string
    }

    let novoLocal = $state('')
    let novoNumero = $state('')
    let novoCep = $state('')
    let novoPontoReferencia = $state('')
    let novoLogradouro = $state('')
    let novoCidadeId = $state<number> (0)

    let listaCidades = $state<Cidade[]>([])

async function listarEndereco(){
    try{
        const res = await getApi("/api/cidade/listar")
        listaCidades = res
    }catch(error){

    }
}


async function cadastrarEndereco(event: SubmitEvent) {
    event.preventDefault()
    const payload = {
        nomeLocal: novoLocal,
        numero: novoNumero,
        cep: novoCep,
        pontoReferencia: novoPontoReferencia,
        logradouroEnum: novoLogradouro,
        cidadeId: novoCidadeId
    }

    try{
        const res = await postApi("/api/endereco/cadastrar", payload)
    }catch(error){

    }
}

onMount(()=> {
    listarEndereco()
})
</script>

<form onsubmit={cadastrarEndereco}>
    <input type="text" bind:value={novoLocal}>
    <input type="text" bind:value={novoNumero}>
    <input type="text" bind:value={novoCep}>
    <input type="text" bind:value={novoPontoReferencia}>
    <input type="text" bind:value={novoLogradouro}>

    <div>
        <select name="" id="" bind:value={novoCidadeId}>
            {#each listaCidades as cid }
                <option value={cid.id}>{cid.nome}</option>
            {/each}
        </select>
    </div>

    <div>
        <button type="submit">Cadastrar</button>
    </div>
</form>

