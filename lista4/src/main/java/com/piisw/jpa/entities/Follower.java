package com.piisw.jpa.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NamedEntityGraph(
        name = "Follower.withCommentsAndEvents",
        attributeNodes = {
                @NamedAttributeNode(value = "comments", subgraph = "comments-subgraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "comments-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("event")
                        }
                )
        }
)
public class Follower {

    @Id
    @SequenceGenerator(name = "FOLLOWER_ID_GENERATOR", sequenceName = "FOLLOWER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FOLLOWER_ID_GENERATOR")
    private long id;

    private Long userId;

    private LocalDate subscriptionDate;

    @OneToMany(mappedBy = "follower")
    private List<Comment> comments;
}