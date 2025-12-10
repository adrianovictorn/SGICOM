package mercado_municipal.mercado_municipal.security;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import mercado_municipal.mercado_municipal.entity.Usuario;
import mercado_municipal.mercado_municipal.entity.enums.RolesEnum;
import mercado_municipal.mercado_municipal.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCpf(username).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
        
        RolesEnum funcao = usuario.getFuncao();
        if (funcao == null) {
            funcao = RolesEnum.ROLE_USER;
        }

        String role = funcao.name();

        Boolean ativoFlag = usuario.getAtivo();
        boolean ativo = ativoFlag != null && ativoFlag;

        return User.withUsername(usuario.getCpf())
        .password(usuario.getSenha())
        .authorities(role)
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(!ativo)
        .build();
    }

    
}