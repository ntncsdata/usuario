package com.javanauta.business;


import com.javanauta.business.converter.UsuarioConverter;
import com.javanauta.business.dto.UsuarioDTO;
import com.javanauta.infrastructure.entity.Usuario;
import com.javanauta.infrastructure.exceptions.ConflictExceptions;
import com.javanauta.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }


    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public void emailExiste(String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if(existe){
                throw new ConflictExceptions("Email já cadastrado" + email);
            }
        }catch (ConflictExceptions e){
            throw new ConflictExceptions("Email já cadastrado" + e.getCause());
        }
    }

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado " + email));
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }
}
