CREATE DATABASE chat_app;

USE chat_app;

CREATE TABLE users (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,  
    username        VARCHAR(50) NOT NULL UNIQUE,       
    email           VARCHAR(100) NOT NULL UNIQUE,      
    password_hash   VARCHAR(60) NOT NULL,          
    avatar_path     VARCHAR(200) DEFAULT NULL,        
    phone_number    VARCHAR(20) UNIQUE DEFAULT NULL,  -- Thêm trường phone_number
    status          ENUM('online', 'offline', 'busy', 'away') DEFAULT 'offline', 
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login      TIMESTAMP DEFAULT NULL,
    last_activity   TIMESTAMP DEFAULT NULL,

    is_verified     BOOLEAN DEFAULT FALSE,
    email_verified_at TIMESTAMP DEFAULT NULL, 
    login_attempts  SMALLINT DEFAULT 0,
    failed_login_at TIMESTAMP DEFAULT NULL,
    is_locked       BOOLEAN DEFAULT FALSE,
    two_factor_enabled BOOLEAN DEFAULT FALSE,

    role            ENUM('user','admin','moderator') DEFAULT 'user',
    timezone        VARCHAR(50) DEFAULT 'UTC',
    deleted_at      TIMESTAMP DEFAULT NULL,  
    session_id      VARCHAR(255) DEFAULT NULL, 
    
    auth_provider   ENUM('local', 'google', 'facebook', 'github') DEFAULT 'local',
    auth_provider_id VARCHAR(255) DEFAULT NULL,  
    
    settings        JSON DEFAULT NULL,
    

    INDEX idx_users_email (email),
    INDEX idx_users_username (username),
    INDEX idx_users_status (status),
    INDEX idx_users_phone (phone_number),
    INDEX idx_users_deleted_at (deleted_at),
    -- Composite index cho login security
    INDEX idx_login_security (is_locked, login_attempts),
    -- Composite index cho OAuth login
	INDEX idx_oauth_login (auth_provider, auth_provider_id)
);

CREATE TABLE user_settings (
    user_id       BIGINT PRIMARY KEY,
    notification_preferences JSON NOT NULL,
    privacy_settings JSON NOT NULL,
    theme         ENUM('default', 'light', 'dark', 'blue') DEFAULT 'default',
    language      VARCHAR(10) DEFAULT 'en',
    timezone      VARCHAR(50) DEFAULT 'UTC',
    font_size     ENUM('small', 'medium', 'large') DEFAULT 'medium',
    active_status_visible BOOLEAN DEFAULT TRUE,
    auto_download JSON ,
    message_backup BOOLEAN DEFAULT FALSE,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at    TIMESTAMP DEFAULT NULL,
    
    CHECK (language IN ('en', 'vi', 'fr', 'es', 'de', 'zh', 'jp')),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE contacts (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT NOT NULL,
    contact_id      BIGINT NOT NULL,
    relationship    ENUM('friend', 'blocked', 'pending', 'favorite') DEFAULT 'pending',
    nickname        VARCHAR(50) DEFAULT NULL,
    notes           TEXT DEFAULT NULL,  -- Ghi chú về liên hệ
    category        VARCHAR(50) DEFAULT NULL,  -- Phân loại liên hệ
    notification_level ENUM('all', 'mentions', 'none') DEFAULT 'all',  -- Cài đặt thông báo
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status_changed_at TIMESTAMP DEFAULT NULL,
    last_interaction TIMESTAMP DEFAULT NULL,  -- Thời gian tương tác gần nhất
    request_sent_by BIGINT DEFAULT NULL,
    deleted_at      TIMESTAMP DEFAULT NULL,
    
    UNIQUE KEY unique_contact (user_id, contact_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (contact_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (request_sent_by) REFERENCES users(id) ON DELETE SET NULL,
    
    INDEX idx_user_contacts (user_id, relationship),
    INDEX idx_contact_relationship (contact_id, relationship)
);


CREATE TABLE `groups` (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(100) NOT NULL,
    slug           VARCHAR(120) NOT NULL UNIQUE,  -- để dùng friendly URL hoặc query nhanh
    description    TEXT DEFAULT NULL,
    avatar_path    VARCHAR(200) DEFAULT NULL,
    creator_id     BIGINT NOT NULL,
    member_count   INT DEFAULT 1,                -- mặc định có creator là 1 member
    is_private     BOOLEAN DEFAULT FALSE,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at     TIMESTAMP DEFAULT NULL,

    FOREIGN KEY (creator_id) REFERENCES users(id),
    INDEX idx_groups_creator (creator_id),
    INDEX idx_groups_slug (slug),
    INDEX idx_groups_deleted_at (deleted_at)
);

CREATE TABLE group_members (
    group_id     BIGINT NOT NULL,
    user_id      BIGINT NOT NULL,
    role         ENUM('member', 'admin', 'moderator') DEFAULT 'member',
    nickname     VARCHAR(50) DEFAULT NULL,
    is_muted     BOOLEAN DEFAULT FALSE,
    joined_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_active  TIMESTAMP DEFAULT NULL, -- bạn có thể cập nhật qua WebSocket

    PRIMARY KEY (group_id, user_id),
    FOREIGN KEY (group_id) REFERENCES `groups`(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,

    INDEX idx_user_groups (user_id),
    INDEX idx_group_members_role (group_id, role)
);


CREATE TABLE conversations (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    type          ENUM('direct', 'group') NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    mongo_conversation_id VARCHAR(50) UNIQUE,

    user1_id      BIGINT DEFAULT NULL,
    user2_id      BIGINT DEFAULT NULL,

    group_id      BIGINT DEFAULT NULL,

    FOREIGN KEY (user1_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (user2_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (group_id) REFERENCES `groups`(id) ON DELETE SET NULL,

    INDEX idx_direct_conversations (user1_id, user2_id),
    INDEX idx_group_conversations (group_id)
);


ALTER TABLE users MODIFY COLUMN auth_provider ENUM('LOCAL','GOOGLE','FACEBOOK','GITHUB') NOT NULL DEFAULT 'LOCAL';

ALTER TABLE users MODIFY COLUMN role ENUM('USER','ADMIN','MODERATOR') NOT NULL DEFAULT 'USER';

ALTER TABLE users MODIFY COLUMN status ENUM('ONLINE','OFFLINE','BUSY','AWAY') NOT NULL DEFAULT 'OFFLINE';